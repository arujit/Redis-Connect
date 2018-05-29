package com.goibibo.connect.redis.models

/**
  * Project: redis-connect
  * Author: shivamsharma
  * Date: 12/2/17.
  */
case class SinkInput(
                                   key: String,
                                   command: String,
                                   value: String
                           )