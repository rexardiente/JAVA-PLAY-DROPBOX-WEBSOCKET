name := """dropbox-API"""
organization := "com.rexardiente"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
	guice,
	"com.dropbox.core" % "dropbox-core-sdk" % "3.1.0",
	"com.fasterxml.jackson.core" % "jackson-core" % "2.9.8",
	"javax.servlet" % "javax.servlet-api" % "4.0.1" % "provided",
  "com.typesafe.akka" %% "akka-actor" % "2.5.22",
  "com.typesafe.play" %% "play-json" % "2.7.2"
)
