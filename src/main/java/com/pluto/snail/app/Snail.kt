package com.pluto.snail.app

import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Interceptor

object Snail {
    fun snail() = Configurator.instance

    private fun <T> getConfigurator(key: Any): T {
        return snail().getConfiguration(key)
    }

    val handler: Handler
        get() = getConfigurator(Confkeys.HANDLER)

    val baseUrl: String
        get() = getConfigurator(Confkeys.API_HOST)


    val interceptor: ArrayList<Interceptor>
        get() = getConfigurator(Confkeys.INTERCEPTOR)


    val activity: AppCompatActivity
        get() = getConfigurator(Confkeys.ACTIVITY)
}