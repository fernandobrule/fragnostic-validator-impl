import sbt._
import Keys._

object Dependencies {

  lazy val logbackClassic             = "ch.qos.logback"      % "logback-classic"                      % "1.3.0-alpha5" % Test
  lazy val scalactic                  = "org.scalactic"       %% "scalactic"                           % "3.2.2" % Test
  lazy val scalatest                  = "org.scalatest"       %% "scalatest"                           % "3.2.2" % Test
  lazy val scalazCore                 = "org.scalaz"          %% "scalaz-core"                         % "7.3.3"
  lazy val emailRfc2822Validator      = "com.github.bbottema" % "emailaddress-rfc2822"                 % "2.2.0"
  lazy val javaxMail                  = "javax.mail"          % "javax.mail-api"                       % "1.6.2"
  
  lazy val fragnosticI18n             = "com.fragnostic"      % "fragnostic-i18n-impl_2.13"            % "0.2.1"
  lazy val fragnosticValidatorApi     = "com.fragnostic"      % "fragnostic-validator-api_2.13"        % "0.3.0-SNAPSHOT"
  lazy val fragnosticFormatter        = "com.fragnostic"      % "fragnostic-formatter_2.13"            % "0.1.0-SNAPSHOT"

}
