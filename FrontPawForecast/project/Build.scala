import sbt._
import Keys._
import play.Project._
import com.github.play2war.plugin._

object ApplicationBuild extends Build {

  val appName         = "FrontPawForecast"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
	"com.typesafe" %% "play-plugins-mailer" % "2.1-RC2",
	"commons-io" % "commons-io" % "2.3"
  )
  
  val main = play.Project(appName, appVersion, appDependencies).settings(
).settings(Play2WarPlugin.play2WarSettings: _*)
 .settings(
    // ... Your own settings here
    Play2WarKeys.servletVersion := "3.0"
	
)
 
}
