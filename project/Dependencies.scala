import sbt._
import Keys._

object Dependencies {

  lazy val logbackClassic             = "ch.qos.logback" %  "logback-classic"                     % "1.2.3"
  lazy val scalactic                  = "org.scalactic"  %% "scalactic"                           % "3.2.2" % "test"
  lazy val scalatest                  = "org.scalatest"  %% "scalatest"                           % "3.2.2" % "test"
  lazy val scalazCore                 = "org.scalaz"     %% "scalaz-core"                         % "7.3.2"

  lazy val fragnosticI18n             = "com.fragnostic" % "fragnostic-i18n_2.13"                 % "0.1.15-SNAPSHOT"
  lazy val fragnosticValidatorApi     = "com.fragnostic" % "fragnostic-validator-api_2.13"        % "0.2.0-SNAPSHOT"

}
