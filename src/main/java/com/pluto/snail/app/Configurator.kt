package com.pluto.snail.app

import android.app.Activity
import android.os.Handler
import com.blankj.utilcode.util.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.pluto.snail.AppContext
import okhttp3.Interceptor

class Configurator private constructor() {
    private val config = HashMap<Any, Any>()
    private val interceptors = ArrayList<Interceptor>()
    private val handler = Handler()

    init {
        config[Confkeys.CONFIG_READY] = false
        config[Confkeys.HANDLER] = handler
    }

    companion object {
        val instance = Configurator()
    }

    fun configurator() {
        Utils.init(AppContext)
        Logger.addLogAdapter(AndroidLogAdapter())
        config[Confkeys.CONFIG_READY] = true
    }


    fun addBaseUrl(host: String): Configurator {
        config[Confkeys.API_HOST] = host
        return this
    }

    fun addActivity(activity: Activity): Configurator {
        config.put(Confkeys.ACTIVITY, activity)
        return this
    }

    fun addInterceptor(interceptor: Interceptor): Configurator {
        interceptors.add(interceptor)
        config[Confkeys.INTERCEPTOR] = interceptors
        return this
    }

    fun addInterceptors(interceptors: ArrayList<Interceptor>): Configurator {
        config[Confkeys.INTERCEPTOR] = interceptors
        return this
    }

    private val isCheck = config[Confkeys.CONFIG_READY]
        ?: throw RuntimeException("Configuration is not ready,call configure")

    fun <T> getConfiguration(key: Any): T {
        isCheck
        config[key] ?: throw NullPointerException("$key IS NULL")
        return config[key] as T
    }

}