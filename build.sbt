val scala3Version = "3.3.1"

lazy val root = project.in(file(".")).settings(
  name := "span_be_coding_test",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := scala3Version,

  assembly / mainClass := Some("App"),
  assembly / assemblyOutputPath := baseDirectory.value / "assembly" / "print_table.jar",
  assembly / test := (Test / test).value,
  cleanFiles += baseDirectory.value / "assembly",

  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"
)
