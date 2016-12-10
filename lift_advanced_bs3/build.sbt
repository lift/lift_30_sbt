name := "Lift v3.0 Template Application with Bootstrap v3"

version := "1.0.0-SNAPSHOT"

organization := "net.liftweb"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
  "staging"       at "https://oss.sonatype.org/content/repositories/staging",
  "releases"      at "https://oss.sonatype.org/content/repositories/releases"
)

enablePlugins(JettyPlugin)

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= {
  val liftVersion = "3.0"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile",
    "net.liftmodules"   %% "fobo_3.0"           % "1.7"              % "compile",
    "ch.qos.logback"    % "logback-classic"     % "1.1.3",
    "org.specs2"        %% "specs2"             % "3.7"              % "test",
    "com.h2database"    % "h2"                  % "1.4.187",
    "javax.servlet"     % "javax.servlet-api"   % "3.0.1"            % "provided"
  )
}
