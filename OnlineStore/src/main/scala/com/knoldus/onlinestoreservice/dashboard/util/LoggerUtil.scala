package com.knoldus.onlinestoreservice.dashboard.util

import org.apache.log4j.Logger

/**
 * LoggerUtil is a Trait which contains the Logger object used in different modules to write logs.
 */
trait LoggerUtil {

  val logger: Logger = Logger.getLogger(this.getClass.getName)

}

/**
 * LoggerUtil is a Singleton object to access the logger object from LoggerUtil Trait.
 */
object LoggerUtil extends LoggerUtil
