organization := "de.hackundsoehne"
name := "invoice-gen"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.3"

val Http4sVersion = "0.17.6"
val Specs2Version = "4.0.2"
val LogbackVersion = "1.2.3"

libraryDependencies ++= Seq(
  "org.http4s"         %% "http4s-blaze-server"  % Http4sVersion,
  "org.http4s"         %% "http4s-circe"         % Http4sVersion,
  "org.http4s"         %% "http4s-dsl"           % Http4sVersion,
  "org.specs2"         %% "specs2-core"          % Specs2Version % "test",
  "ch.qos.logback"     %  "logback-classic"      % LogbackVersion,
  "io.circe"           %% "circe-parser"         % "0.6.1",

  // Auto-derivation of JSON codecs
  "io.circe"           %% "circe-generic"        % "0.6.1",

  // String interpolation to JSON model
  "io.circe"           %% "circe-literal"        % "0.6.1",

  // Slick DB drivers
  "com.typesafe.slick" %% "slick"                % "3.2.1",
  //"org.slf4j"          %  "slf4j-nop"            % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp"       % "3.2.1",

  // SQLite JDBC driver
  "org.xerial"         % "sqlite-jdbc"           % "3.21.0.1"
)

