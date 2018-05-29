name := "redis-connect"

version := "0.2"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
    "org.apache.kafka" % "connect-api" % "0.10.1.0",
    "redis.clients" % "jedis" % "2.9.0",
    "org.slf4j" % "slf4j-simple" % "1.7.25",
    "org.json4s" %% "json4s-native" % "3.5.2",
    "org.json4s" %% "json4s-jackson" % "3.5.2",
    "com.newrelic.agent.java" % "newrelic-api" % "3.33.0"
)