package com.goibibo.connect.redis.models

import java.util.Date

/**
  * Project: redis-connect
  * Author: shivamsharma
  * Date: 12/2/17.
  */
case class PersuasionOutput(
                                   key: String,
                                   value: Long,
                                   startTime: Date,
                                   endTime: Date
                           )