import sbt._

object Dependencies {

  lazy val fragnosticFormatter        = "com.fragnostic"        % "fragnostic-formatter_2.13"      % "0.1.1"
  lazy val fragnosticI18nImpl         = "com.fragnostic"        % "fragnostic-i18n-impl_2.13"      % "0.2.6-SNAPSHOT"
  lazy val fragnosticValidatorApi     = "com.fragnostic"        % "fragnostic-validator-api_2.13"  % "0.3.5-SNAPSHOT"
  lazy val fragnosticConfFacade       = "com.fragnostic"        %  "fragnostic-conf-facade_2.13"   % "0.2.7-SNAPSHOT" % Test

  lazy val emailRfc2822Validator      = "com.github.bbottema"   % "emailaddress-rfc2822"           % "2.2.0"
  lazy val javaxMail                  = "javax.mail"            % "javax.mail-api"                 % "1.6.2"
  lazy val logbackClassic             = "ch.qos.logback"        % "logback-classic"                % "1.3.0-alpha12" % "runtime"
  lazy val scalatestFunSpec           = "org.scalatest"         % "scalatest-funspec_2.13"         % "3.3.0-SNAP3" % Test
  lazy val scalazCore                 = "org.scalaz"            % "scalaz-core_2.13"               % "7.4.0-M9"

}
