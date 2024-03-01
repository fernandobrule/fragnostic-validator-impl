import sbt._

object Dependencies {

  lazy val emailRfc2822Validator      = "com.github.bbottema"   % "emailaddress-rfc2822"           % "2.3.1"
  lazy val fragnosticConfBase         = "com.fragnostic"        % "fragnostic-conf-base_2.13"      % "0.1.8"
  lazy val fragnosticConfCache        = "com.fragnostic"        % "fragnostic-conf-cache_2.13"     % "0.2.10"
  lazy val fragnosticConfFacade       = "com.fragnostic"        %  "fragnostic-conf-facade_2.13"   % "0.2.9" % Test
  lazy val fragnosticFormatter        = "com.fragnostic"        % "fragnostic-formatter_2.13"      % "0.1.2"
  lazy val fragnosticI18nApi          = "com.fragnostic"        % "fragnostic-i18n-api_2.13"       % "0.1.4"
  lazy val fragnosticI18nImpl         = "com.fragnostic"        % "fragnostic-i18n-impl_2.13"      % "0.2.8"
  lazy val fragnosticSupport          = "com.fragnostic"        % "fragnostic-support_2.13"        % "0.1.19"
  lazy val fragnosticValidatorApi     = "com.fragnostic"        % "fragnostic-validator-api_2.13"  % "0.3.6"
  lazy val javaxMail                  = "javax.mail"            % "javax.mail-api"                 % "1.6.2"
  lazy val lettuce                    = "io.lettuce"            %  "lettuce-core"                  % "6.3.1.RELEASE"
  lazy val logbackClassic             = "ch.qos.logback"        % "logback-classic"                % "1.5.0" % "runtime"
  lazy val scalatestFunSpec           = "org.scalatest"        %% "scalatest-funspec"              % "3.2.18" % Test
  lazy val scalazCore                 = "org.scalaz"            % "scalaz-core_2.13"               % "7.3.8"

}
