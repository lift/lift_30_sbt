package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper._

import code.model._
import net.liftmodules.{FoBo,FoBoBs}

import scala.language.postfixOps

import net.liftweb.http.ContentSourceRestriction._


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      sys.props.put("h2.implicitRelativePath", "true")
      val vendor = new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
        Props.get("db.url") openOr
        "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
        Props.get("db.user"), Props.get("db.password"))
      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)
      DB.defineConnectionManager(util.DefaultConnectionIdentifier, vendor)
    }

    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
    Schemifier.schemify(true, Schemifier.infoF _, User)

    // where to search snippet
    LiftRules.addToPackages("code")

    def sitemapMutators = User.sitemapMutator
    //The SiteMap is built in the Site object bellow
    LiftRules.setSiteMapFunc(() => sitemapMutators(Site.sitemap))

    //Init the FoBo - Front-End Toolkit module,
    //see http://liftweb.net/lift_modules for more info
    FoBo.Toolkit.Init=FoBo.Toolkit.JQuery310
    FoBo.Toolkit.Init=FoBo.Toolkit.Bootstrap337
    FoBo.Toolkit.Init=FoBo.Toolkit.FontAwesome463

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    LiftRules.noticesAutoFadeOut.default.set( (notices: NoticeType.Value) => {
        notices match {
          case NoticeType.Notice => Full((8 seconds, 4 seconds))
          case _ => Empty
        }
     }
    )

    //Lift CSP settings see http://content-security-policy.com/ and
    //Lift API for more information.
    //Like the name indicates, using UnsafeInline is ... not safe. It is used here as an example.
    LiftRules.securityRules = () => {
      SecurityRules(content = Some(ContentSecurityPolicy(
        scriptSources = List(
            ContentSourceRestriction.Self, UnsafeInline),
        styleSources = List(
            ContentSourceRestriction.Self, UnsafeInline)
            )))
    }
    // Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)
  }

  object Site {
    import scala.xml._
    val divider1   = Menu("divider1") / "divider1"
    val ddLabel1   = Menu.i("UserDDLabel") / "ddlabel1"
    val home       = Menu.i("Home") / "index"
    val userMenu   = User.AddUserMenusHere
    val static     = Menu(Loc("Static",
        Link(List("static"), true, "/static/index"),
        S.loc("StaticContent" , scala.xml.Text("Static Content")),
        LocGroup("lg2","topRight")))
    val twbs  = Menu(Loc("twbs",
        ExtLink("http://getbootstrap.com/"),
        S.loc("Bootstrap3", Text("Bootstrap3")),
        LocGroup("lg2"),
        FoBoBs.BSLocInfo.LinkTargetBlank ))
    val foboGithub  = Menu(Loc("foboGithub",
        ExtLink("https://github.com/karma4u101/FoBo/"),
        S.loc("FoBoGithub", Text("FoBoGithub")),
        LocGroup("lg2"),
        FoBoBs.BSLocInfo.LinkTargetBlank ))

    def sitemap = SiteMap(
        home          >> LocGroup("lg1"),
        static,
        twbs,
        foboGithub,
        ddLabel1      >> LocGroup("topRight") >> PlaceHolder submenus (
            divider1  >> FoBoBs.BSLocInfo.Divider >> userMenu
            )
         )
  }

}
