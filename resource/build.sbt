import ReleaseTransformations._

name := "resource"
organization := "org.clulab"

crossPaths := false // This is a resource only and is independent of Scala version.

lazy val core = (project in file("."))

publishMavenStyle := true

publishTo := {
  val artifactory = "http://localhost:8081/artifactory/"
  
  if (isSnapshot.value)
    Some("Artifactory Realm" at artifactory + "sbt-release-local;build.timestamp=" + new java.util.Date().getTime)
  else
    Some("Artifactory Realm" at artifactory + "sbt-release-local")
}

// Figure out how to do this with external credentials
// So that a line like this does not 
credentials += Credentials("Artifactory Realm", "localhost", "kwalcock", "APAgSddqWKTn2e9sJF73VPd46Zs")

// Let’s remove any repositories for optional dependencies in our artifact.
pomIncludeRepository := { _ => false }

// These values in scmInfo replace the <scm/> section previously recorded in
// pomExtra so that default values aren't used which then double up in the
// XML and cause a validation error.  This problem was first noted with
// sbt.version=1.1.6
// addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.8")
// addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.3")
// This produced
// <scm>
//     <url>https://github.com/clulab/resource</url>
//     <connection>scm:git:https://github.com/clulab/resource.git</connection>
//     <developerConnection>scm:git:git@github.com:clulab/resource.git</developerConnection>
// </scm>
// that must be automatically generated and a duplicate
// <scm>
//     <url>https://github.com/clulab/resource</url>
//     <connection>https://github.com/clulab/resource</connection>
// </scm>
// Judging from this, the scmInfo is collected automatically, perhaps by
// addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")
// However, the developerConnection is undesired, so this is used:
scmInfo := Some(
  ScmInfo(
    url("https://github.com/clulab/resource"),
    "scm:git:https://github.com/clulab/resource.git"
  )
)

// This must be added to add to the pom for publishing.
pomExtra :=
  <url>https://github.com/clulab/resource</url>
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <!--scm>
    <url>https://github.com/clulab/resource</url>
    <connection>https://github.com/clulab/resource</connection>
  </scm-->
  <developers>
    <developer>
      <id>mihai.surdeanu</id>
      <name>Mihai Surdeanu</name>
      <email>mihai@surdeanu.info</email>
    </developer>
  </developers>


releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  setNextVersion,
  commitNextVersion,
//  releaseStepCommandAndRemaining("sonatypeReleaseAll"),
  pushChanges
)

//git.remoteRepo := "git@github.com:clulab/resource.git"
