import AssemblyKeys._ // put this at the top of the file

assemblySettings

excludedJars in assembly <<= (fullClasspath in assembly) map { cp => 
    cp filter {_.data.getName == "jfxrt.jar"}
}

// proguardSettings

// ProguardKeys.options in Proguard ++= Seq("-dontwarn", "-ignorewarnings", "-verbose", "-dontoptimize", "-dontobfuscate")

// ProguardKeys.options in Proguard += ProguardOptions.keepMain("co.uproot.abandon.AbandonApp")

lazy val commonSettings = Seq(
  // don't define "name" here, because it will cause
  // circular dependencies with sub-projects

  organization := "in.co.uproot",
  version := "0.3.0-rc1",
  scalaVersion := "2.11.8",
  scalacOptions := List("-deprecation"),
  wartremoverWarnings ++= Warts.allBut(Wart.ToString, Wart.Throw),

  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  }

  )

lazy val abandon = (project in file(".")).
  aggregate(base, cli, gui).
  dependsOn(base, cli, gui).
  settings(commonSettings: _*).
  settings(
    name := "abandon",
    fork in run := true
  )

lazy val base = (project in file("base")).
  settings(commonSettings: _*).
  settings(
    name := "abandon-base",
    fork in run := true
  )


lazy val cli = (project in file("cli")).
  dependsOn(base).
  settings(commonSettings: _*).
  settings(
    name := "abandon-cli",
    fork in run := true
  )

lazy val gui = (project in file("gui")).
  dependsOn(base).
  settings(commonSettings: _*).
  settings(
    name := "abandon-gui",
    fork in run := true
  )



