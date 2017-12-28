package com.goibibo.connect.redis

import com.goibibo.connect.redis.models.PersuasionOutput
import org.slf4j.{Logger, LoggerFactory}
import redis.clients.jedis.Pipeline

/**
  * Project: redis-connect
  * Author: shivamsharma
  * Date: 12/13/17.
  */
object Util {
    private val logger: Logger = LoggerFactory.getLogger(this.getClass)

    def addToPipeline(pipeline:Pipeline, output: PersuasionOutput): Unit = {
        output.command match {
            case "incrBy" => pipeline.incrBy(output.key, output.value.toLong)
            case "expireAt" => pipeline.expireAt(output.key, output.value.toLong)
            case "lpush" => pipeline.lpush(output.key, output.value)
            case _ => logger.info(s"No command matches. Command: ${output.command}")
        }
    }

}
