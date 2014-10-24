import sbt._
import Keys._

import play.PlayScala

object Play2ChainActionBuild extends Build {
  val _name         = "Play2-ChainAction"
  val _organization = "com.github.rabitarochan"
  val _version      = "0.1"
  val _scalaVersion = "2.11.2"

  lazy val baseSettings = Seq(
    organization := _organization,
    version := _version
  )

  lazy val publishSettings = Seq(
    publishMavenStyle := true,
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT")) {
        Some("snapshots" at nexus + "content/repositories/snapshots")
      } else {
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
      }
    },
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    pomExtra := (
      <url>https://github.com/rabitarochan/Play2-ChainAction</url>
      <licenses>
        <license>
          <name>Apache License, Version 2.0</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:rabitarochan/Play2-ChainAction.git</url>
        <connection>scm:git:git@github.com:rabitarochan/Play2-ChainAction.git</connection>
      </scm>
      <developers>
        <developer>
          <id>rabitarochan</id>
          <name>Kengo Asamizu</name>
          <url>https://github.com/rabitarochan</url>
        </developer>
      </developers>
    )
  )

  lazy val root = Project("root", file("."))
    .settings(baseSettings : _*)
    .aggregate(core, example)

  lazy val core = Project("core", file("core"))
    .settings(baseSettings : _*)
    .settings(publishSettings : _*)
    .settings(
      name := _name,
      scalaVersion := _scalaVersion,
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play" % "2.3.4" % "provided"
      )
    )

  lazy val example = Project("example", file("example"))
    .enablePlugins(PlayScala)
    .settings(
      scalaVersion := _scalaVersion,
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play" % "2.3.4" % "provided"
      )
    ).dependsOn(core)

}
