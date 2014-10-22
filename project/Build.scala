import sbt._
import Keys._

import play.PlayScala

object Play2ChainActionBuild extends Build {

  val baseSettings = Seq(
    organization := "com.github.rabitarochan",
    version := "0.1-SNAPSHOT"
  )

  lazy val root = Project("root", file("."))
    .settings(baseSettings : _*)
    .aggregate(core, example)

  lazy val core = Project("core", file("core"))
    .settings(baseSettings : _*)
    .settings(
      name := "Play2-ChainAction",
      scalaVersion := "2.11.2",
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play" % "2.3.4" % "provided"
      )
    )

  lazy val example = Project("example", file("example"))
    .enablePlugins(PlayScala)
    .settings(
      scalaVersion := "2.11.2",
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play" % "2.3.4" % "provided"
      )
    ).dependsOn(core)

}
