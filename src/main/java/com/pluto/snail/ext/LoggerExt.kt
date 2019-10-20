package com.pluto.charon.ext

import com.orhanobut.logger.Logger

object logger {

    fun d(msg: String) {
        Logger.d(msg)
    }


    fun v(msg: String) {
        Logger.v(msg)
    }


    fun w(msg: String) {
        Logger.w(msg)
    }


    fun i(msg: String) {
        Logger.i(msg)
    }


    fun e(msg: String) {
        Logger.e(msg)
    }
}