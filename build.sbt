import com.jsuereth.sbtpgp.PgpKeys
import scala.xml._
import java.net.URL
import Dependencies._

val unusedOptions = Seq("-Ywarn-unused:imports")

lazy val frgValidatorImplSettings = Seq(
  organization := "com.fragnostic",
  Test / fork := true,
  Test / baseDirectory := (ThisBuild / baseDirectory).value,
  crossScalaVersions := Seq("2.12.14", "2.13.6"),
  scalaVersion := crossScalaVersions.value.head,
  allDependencies := {
    allDependencies.value
  },
  testFrameworks --= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, _)) =>
        Nil
      case _ =>
        // specs2 does not support Scala 3
        // TODO remove this setting when specs2 for Scala 3 released
        Seq(TestFrameworks.Specs2)
    }
  },
  scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, _)) =>
        unusedOptions ++ Seq(
          "-target:jvm-1.8",
          "-Xlint",
          "-Xcheckinit",
        )
      case _ =>
        Seq(
          "-Xignore-scala2-macros",
          "-source",
          "3.0-migration",
        )
    }
  },
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    /*"-Yinline-warnings",*/
    "-encoding", "utf8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:existentials"
  ),
  manifestSetting,
) ++ mavenCentralFrouFrou ++ Seq(Compile, Test).flatMap(c =>
  c / console / scalacOptions --= unusedOptions
)

lazy val frgValidatorImplProject = Project(
  id = "fragnostic-validator-impl-project",
  base = file(".")).settings(
    frgValidatorImplSettings ++ Seq(
    name := "fragnostic validator impl project",
    artifacts := Classpaths.artifactDefs(Seq(Compile / packageDoc, Compile / makePom)).value,
    packagedArtifacts := Classpaths.packaged(Seq(Compile / packageDoc, Compile / makePom)).value,
    description := "fragnostic validator impl project",
    shellPrompt := { state =>
      s"sbt:${Project.extract(state).currentProject.id}" + Def.withColor("> ", Option(scala.Console.CYAN))
    }
  ) ++ Defaults.packageTaskSettings(
    Compile / packageDoc, (Compile / unidoc).map(_.flatMap(Path.allSubpaths))
  )).aggregate(
    frgValidatorImpl
  ).enablePlugins(ScalaUnidocPlugin)


//
// fragnostic validator impl
//
lazy val frgValidatorImpl = Project(
  id = "fragnostic-validator-impl",
  base = file("fragnostic-validator-impl")).settings(
    frgValidatorImplSettings ++ Seq(
    libraryDependencies ++= Seq(
      fragnosticI18nImpl,
      fragnosticValidatorApi,
      fragnosticFormatter,
      emailRfc2822Validator,
      javaxMail,
      logbackClassic,
      scalatestFunSpec,
      scalazCore
    ),
    description := "fragnostic validator impl"
  )
) dependsOn(
  //
)


lazy val manifestSetting = packageOptions += {
  Package.ManifestAttributes(
    "Created-By" -> "Simple Build Tool",
    "Built-By" -> System.getProperty("user.name"),
    "Build-Jdk" -> System.getProperty("java.version"),
    "Specification-Title" -> name.value,
    "Specification-Version" -> version.value,
    "Specification-Vendor" -> organization.value,
    "Implementation-Title" -> name.value,
    "Implementation-Version" -> version.value,
    "Implementation-Vendor-Id" -> organization.value,
    "Implementation-Vendor" -> organization.value
  )
}


lazy val mavenCentralFrouFrou = Seq(
  homepage := Some(new URL("http://www.distronica-admin.org/")),
  startYear := Some(2009),
  licenses := Seq(("BSD", new URL("http://github.com/distronica-admin/dist/raw/HEAD/LICENSE"))),
  pomExtra := pomExtra.value ++ Group(
    <scm>
      <url>http://github.com/fragnostic/validator</url>
      <connection>scm:git:git://github.com/fragnostic-validator-impl/dist.git</connection>
    </scm>
    <developers>
      <developer>
        <id>fernandobrule</id>
        <name>Fernando Brûlé</name>
        <url>http://www.fernando-brule.info</url>
      </developer>
    </developers>
  )
)


lazy val doNotPublish = Seq(publish := {}, publishLocal := {}, PgpKeys.publishSigned := {}, PgpKeys.publishLocalSigned := {})

