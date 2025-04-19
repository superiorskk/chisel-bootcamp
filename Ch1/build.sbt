ThisBuild / scalaVersion := "2.13.16"
ThisBuild / version := "1.0.0"
ThisBuild / organization := "chisel-bootcamp"

val chiselVersion = "6.7.0"
val scalatestVersion = "3.2.19"

lazy val root = (project in file("."))
	.settings(
		name := "chisel-bootcamp",
		libraryDependencies ++= Seq(
			"org.chipsalliance" %% "chisel" % chiselVersion,
			"org.scalatest" %% "scalatest" % scalatestVersion % "test",
		),
		scalacOptions ++= Seq(
			"-language:reflectiveCalls",
			"-deprecation",
			"-feature",
			"-Xcheckinit",
			"-Ymacro-annotations",
		),
		addCompilerPlugin("org.chipsalliance" %% "chisel-plugin" % chiselVersion cross CrossVersion.full),
	)