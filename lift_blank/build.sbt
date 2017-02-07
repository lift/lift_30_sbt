name := "Lift 3.0 starter template"

version := "0.1.0"

organization := "net.liftweb"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
  "releases"        at "https://oss.sonatype.org/content/repositories/releases"
)

enablePlugins(JettyPlugin)

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "3.0.1"
  Seq(
    "net.liftweb"       %% "lift-webkit"            % liftVersion,
    "net.liftmodules"   %% "lift-jquery-module_3.0" % "2.9",
    "javax.servlet"     % "javax.servlet-api"       % "3.0.1"       % "provided",
    "ch.qos.logback"    % "logback-classic"         % "1.1.3",
    "org.specs2"        %% "specs2-core"            % "3.6.4"       % "test"
  )
}

scalacOptions in Test ++= Seq("-Yrangepos")
