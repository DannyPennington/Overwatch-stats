name := """Overwatch-stats"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.11"


libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.11-play28",
  "org.slf4j" % "slf4j-api" % "1.7.30",
  "codes.reactive" %% "scala-time" % "0.4.2",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3",
  "org.mindrot" % "jbcrypt" % "0.4"
)
