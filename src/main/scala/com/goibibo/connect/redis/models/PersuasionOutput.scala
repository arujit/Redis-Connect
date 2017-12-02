package com.goibibo.connect.redis.models

import java.sql.Timestamp

/**
  * Project: redis-connect
  * Author: shivamsharma
  * Date: 12/2/17.
  */
case class PersuasionOutput(
                                   key: String,
                                   value: Long,
                                   startTime: Timestamp,
                                   endTime: Timestamp
                           )