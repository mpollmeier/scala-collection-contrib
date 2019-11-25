name := "scala-collection-contrib"
organization := "com.michaelpollmeier"
scalaVersion := "2.13.1"
enablePlugins(GitVersioning)

scalacOptions ++= Seq("-opt-warnings", "-language:higherKinds", "-deprecation", "-feature", "-Xfatal-warnings")
scalacOptions in (Compile, doc) ++= Seq("-implicits", "-groups")
testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "-a")
parallelExecution in Test := false  // why?
libraryDependencies ++= Seq(
  "junit"            % "junit"           % "4.12"   % Test,
  "com.novocode"     % "junit-interface" % "0.11"   % Test,
  "org.openjdk.jol"  % "jol-core"        % "0.9"    % Test
)

bintrayVcsUrl := Some("https://github.com/mpollmeier/scala-collection-contrib")
licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
