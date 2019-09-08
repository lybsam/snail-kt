package com.pluto.charon.ext

import com.orhanobut.logger.Logger

object Logger {

    private val VERBOSE = 1
    private val DEBUG = 2
    private val INFO = 3
    private val WARN = 4
    private val ERROR = 5
    private val NOTHING = 6

    //控制log等级
    private val LEVEL = VERBOSE

    fun v(tag: String, message: String) {
        Logger.t(tag).v(message)
    }

    fun d(tag: String, message: Any) {
        Logger.t(tag).d(message)
    }

    fun d(message: Any) {
        Logger.d(message)
    }

    fun i(tag: String, message: String) {
        Logger.t(tag).i(message)
    }

    fun w(tag: String, message: String) {
        Logger.t(tag).w(message)
    }

    fun json(tag: String, message: String) {
        Logger.t(tag).json(message)
    }

    fun e(tag: String, message: String) {
        Logger.t(tag).e(message)
    }


    fun e(message: String){
        Logger.e(message)
    }
}
