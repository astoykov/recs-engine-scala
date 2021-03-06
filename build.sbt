import AssemblyKeys._

name := "recs-engine-scala"

version := "1.0"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "com.typesafe.akka" %% "akka-actor" % "2.3.4",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.4",
  "io.spray" %% "spray-can" % "1.3.1",
  "io.spray" %% "spray-routing" % "1.3.1",
  "io.spray" %% "spray-json" % "1.3.0",
  "org.apache.httpcomponents" % "httpclient" % "4.4.1",
  "org.apache.httpcomponents" % "httpcore" % "4.4.1",
  "commons-codec" % "commons-codec" % "1.10",
  "commons-logging" % "commons-logging" % "1.2",
  "org.scalatest" %% "scalatest" % "2.2.0" % "test"
)

assemblySettings

mainClass in assembly := Some("com.sky.assignment.Application")
    