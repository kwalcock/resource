
name := "resource-client"
organization := "org.clulab"

scalaVersion := "2.12.4"

resolvers += "Artifactory" at "http://localhost:8081/artifactory/sbt-release-local/"


libraryDependencies ++= {
  Seq(
    "org.clulab" % "resource" % "0.1.0"
  )
}

lazy val core = (project in file("."))
