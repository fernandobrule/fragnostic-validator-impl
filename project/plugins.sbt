scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

addSbtPlugin("ch.epfl.scala"        % "sbt-scalafix"         % "0.11.1")
addSbtPlugin("com.github.sbt"       % "sbt-pgp"              % "2.2.1")
addSbtPlugin("com.github.sbt"       % "sbt-unidoc"           % "0.5.0")
addSbtPlugin("org.scalariform"      % "sbt-scalariform"      % "1.8.3")
addSbtPlugin("org.xerial.sbt"       % "sbt-sonatype"         % "3.9.21")
