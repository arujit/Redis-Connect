package com.goibibo.connect.redis

import java.text.SimpleDateFormat
import java.util

import com.goibibo.connect.redis.models.PersuasionOutput
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.connect.sink.{SinkRecord, SinkTask}
import org.json4s.jackson.JsonMethods._
import org.json4s.{DefaultFormats, _}
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

    override def start(props: util.Map[String, String]): Unit = {
        config = RedisSinkConfig(props.asScala.toMap)
        jedis = new Jedis(config.redisHost, config.redisPort)
        jedis.select(config.redisDatabase)
    }

    override def put(records: util.Collection[SinkRecord]): Unit = {
        lazy implicit val formats = new DefaultFormats {
            override def dateFormatter = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss")
        }
        val persuasionOutputs = records.asScala.toList.map { p =>
            parse(p.value().toString).extract[PersuasionOutput]
        }
        println()
        val pipeline: Pipeline = jedis.pipelined()
        persuasionOutputs.foreach { p =>
            //Minute level data needs to be expired in 2 hours
            pipeline.incrBy(p.key, p.value)
            pipeline.expire(p.key, 7200)

            // Hours level data needs to be expired in 2 days
            pipeline.incrBy(p.key.dropRight(2), p.value)
            pipeline.expire(p.key.dropRight(2), 172800)

            // Hours level data needs to be expired in 2 months
            pipeline.incrBy(p.key.dropRight(4), p.value)
            pipeline.expire(p.key.dropRight(4), 5184000)
        }
        pipeline.sync()
    }

    override def stop(): Unit = {

    }

    override def flush(offsets: util.Map[TopicPartition, OffsetAndMetadata]): Unit = {

    }

    override def version(): String = "0.1.0-Snapshot"
}
