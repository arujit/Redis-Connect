package com.goibibo.connect.redis

import java.text.SimpleDateFormat
import java.util

import com.goibibo.connect.redis.models.PersuasionOutput
import com.newrelic.api.agent.NewRelic
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.connect.sink.{SinkRecord, SinkTask}
import org.json4s.jackson.JsonMethods._
import org.json4s.{DefaultFormats, _}
import org.slf4j.{Logger, LoggerFactory}
import redis.clients.jedis.{Jedis, Pipeline}

import scala.collection.JavaConverters._

/**
  * Project: redis-connect
  * Author: shivamsharma
  * Date: 12/2/17.
  */
class RedisSinkTask extends SinkTask {
    private var config: RedisSinkConfig = _
    private var jedis: Jedis = _
    private var logger: Logger = LoggerFactory.getLogger(RedisSinkConfig.getClass)

    override def start(props: util.Map[String, String]): Unit = {
        config = RedisSinkConfig(props.asScala.toMap)
        jedis = new Jedis(config.redisHost, config.redisPort)
        jedis.select(config.redisDatabase)
    }

    override def put(records: util.Collection[SinkRecord]): Unit = {
        lazy implicit val formats: DefaultFormats = new DefaultFormats {
            override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        }
        val persuasionOutputs = records.asScala.toList.map { p =>
            parse(p.value().toString).extract[PersuasionOutput]
        }
        try {
            val pipeline: Pipeline = jedis.pipelined()
            persuasionOutputs.foreach { p: PersuasionOutput =>
                Util.addToPipeline(pipeline, p)
            }
            pipeline.sync()
          NewRelic.incrementCounter("Custom/RedisConnect-Input")
        } catch {
            case e: Exception =>
                logger.error("Exception occurred in Redis Connect... Exiting the application", e)
                System.exit(1)
        }
    }

    override def stop(): Unit = {

    }

    override def flush(offsets: util.Map[TopicPartition, OffsetAndMetadata]): Unit = {

    }

    override def version(): String = "0.1.0-Snapshot"
}
