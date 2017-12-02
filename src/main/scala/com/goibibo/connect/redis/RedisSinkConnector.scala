package com.goibibo.connect.redis

import java.util

import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.sink.SinkConnector

import scala.collection.JavaConverters._

/**
  * Project: redis-connect
  * Author: shivamsharma
  * Date: 12/2/17.
  */
class RedisSinkConnector extends SinkConnector {

    var props: Map[String, String] = _

    override def start(props: util.Map[String, String]): Unit = {
        this.props = props.asScala.toMap
    }

    override def taskClass(): Class[RedisSinkTask] = classOf[RedisSinkTask]

    override def version(): String = "0.1.0-Snapshot"

    override def stop(): Unit = {}

    override def taskConfigs(maxTasks: Int): util.List[util.Map[String, String]] = List.fill(maxTasks)(props.asJava).asJava

    override def config(): ConfigDef = RedisSinkConfig.configDef
}
