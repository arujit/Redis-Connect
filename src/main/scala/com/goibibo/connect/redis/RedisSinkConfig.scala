package com.goibibo.connect.redis

import org.apache.kafka.common.config.{AbstractConfig, ConfigDef}

import scala.collection.JavaConverters._

/**
  * Project: redis-connect
  * Author: shivamsharma
  * Date: 12/2/17.
  */
class RedisSinkConfig(definition: ConfigDef, props: Map[String, String]) extends AbstractConfig(definition, props.asJava) {
    val redisHost: String = getString(RedisSinkConfig.REDIS_HOST)
    val redisPort: Int = getInt(RedisSinkConfig.REDIS_PORT)
    val redisDatabase: Int = getInt(RedisSinkConfig.REDIS_DATABASE)
}

object RedisSinkConfig {

    private val REDIS_HOST = "redis.host"
    private val REDIS_PORT = "redis.port"
    private val REDIS_DATABASE = "redis.database"

    def apply(props: Map[String, String]): RedisSinkConfig = new RedisSinkConfig(configDef, props)

    final val configDef = new ConfigDef()
            .define(REDIS_HOST, ConfigDef.Type.STRING, ConfigDef.NO_DEFAULT_VALUE, ConfigDef.Importance.HIGH,
                "Redis Host")
            .define(REDIS_PORT, ConfigDef.Type.INT, 6379, ConfigDef.Importance.LOW, "Redis Port")
            .define(REDIS_DATABASE, ConfigDef.Type.INT, 0, ConfigDef.Importance.LOW, "Redis Database")
}
