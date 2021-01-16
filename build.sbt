import com.jsuereth.sbtpgp.PgpKeys
import scala.xml._
import java.net.URL
import Dependencies._

val unusedOptions = Def.setting(
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 11)) =>
      Seq("-Ywarn-unused-import")
    case _ =>
      Seq("-Ywarn-unused:imports")
  }
)

lazy val frgValidatorImplSettings = Seq(
  organization := "com.fragnostic",
  fork in Test := true,
  baseDirectory in Test := file("."),
  crossScalaVersions := Seq("2.12.12", "2.11.12", "2.13.4"),
  scalaVersion := crossScalaVersions.value.head,
  scalacOptions ++= unusedOptions.value,
  scalacOptions ++= Seq(
    "-target:jvm-1.8",
    "-unchecked",
    "-deprecation",
    "-Xlint",
    /*"-Yinline-warnings",*/
    "-Xcheckinit",
    "-encoding", "utf8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:reflectiveCalls",
    "-language:existentials"
  ),
  manifestSetting,
  dependencyOverrides := Seq(
    "org.scala-lang" % "scala-library"  % scalaVersion.value,
    "org.scala-lang" % "scala-reflect"  % scalaVersion.value,
    "org.scala-lang" % "scala-compiler" % scalaVersion.value
  )
) ++ mavenCentralFrouFrou ++ Seq(Compile, Test).flatMap(c =>
  scalacOptions in (c, console) --= unusedOptions.value
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

// Things we care about primarily because Maven Central demands them
lazy val mavenCentralFrouFrou = Seq(
  homepage := Some(new URL("http://www.fragnostic.com.br")),
  startYear := Some(2020),
  pomExtra := pomExtra.value ++ Group(
    <developers>
      <developer>
        <id>fbrule</id>
        <name>Fernando Brûlé</name>
        <url>https://github.com/fernandobrule</url>
      </developer>
    </developers>
  )
)

lazy val doNotPublish = Seq(publish := {}, publishLocal := {}, PgpKeys.publishSigned := {}, PgpKeys.publishLocalSigned := {})

lazy val frgValidatorImplProject = Project(
  id = "fragnostic-validator-impl-project",
  base = file(".")).settings(
    frgValidatorImplSettings ++ Seq(
    name := "fragnostic validator impl project",
    artifacts := Classpaths.artifactDefs(Seq(packageDoc in Compile, makePom in Compile)).value,
    packagedArtifacts := Classpaths.packaged(Seq(packageDoc in Compile, makePom in Compile)).value,
    description := "A Fragnostic Validator Impl",
    shellPrompt := { state =>
      s"sbt:${Project.extract(state).currentProject.id}" + Def.withColor("> ", Option(scala.Console.CYAN))
    }
  ) ++ Defaults.packageTaskSettings(
    packageDoc in Compile, (unidoc in Compile).map(_.flatMap(Path.allSubpaths))
  )).aggregate(
    frgValidatorImpl
  ).enablePlugins(ScalaUnidocPlugin)

lazy val frgValidatorImpl = Project(
  id = "fragnostic-validator-impl",
  base = file("fragnostic-validator-impl")).settings(frgValidatorImplSettings ++ Seq(
    libraryDependencies ++= Seq(
      fragnosticI18n,
      fragnosticValidatorApi,
      javaxMail,
      emailRfc2822Validator,
      logbackClassic,
      scalactic,
      scalatest,
      scalazCore
    ),
    description := "fragnostic validator impl"
  )
) dependsOn(
  //
)
