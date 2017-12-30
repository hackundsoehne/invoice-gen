organization := "de.hackundsoehne"
name := "invoice-gen"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.3"

val Http4sVersion = "0.17.6"
val Specs2Version = "4.0.2"
val LogbackVersion = "1.2.3"

libraryDependencies ++= Seq(
  "org.http4s"     %% "http4s-blaze-server"  % Http4sVersion,
  "org.http4s"     %% "http4s-circe"         % Http4sVersion,
  "org.http4s"     %% "http4s-dsl"           % Http4sVersion,
  "org.specs2"     %% "specs2-core"          % Specs2Version % "test",
  "ch.qos.logback" %  "logback-classic"      % LogbackVersion,
  "io.circe"       %% "circe-parser"         % "0.6.1",

  // Auto-derivation of JSON codecs
  "io.circe"       %% "circe-generic"        % "0.6.1",

  // String interpolation to JSON model
  "io.circe"       %% "circe-literal"        % "0.6.1"

)

