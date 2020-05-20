name := "ShapelessAstronaut"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "com.chuusai"   %% "shapeless"              % "2.3.3",
  "org.typelevel" %% "cats-core"              % "2.0.0",
  "io.circe"      %% "circe-core"             % "0.12.3",
  "io.circe"      %% "circe-generic"          % "0.12.3",
  "io.circe"      %% "circe-parser"           % "0.12.3",
  "eu.timepit"    %% "refined"                % "0.9.14",
  "eu.timepit"    %% "refined-cats"           % "0.9.14",
  "eu.timepit"    %% "refined-shapeless"      % "0.9.14"
)