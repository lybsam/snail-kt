package com.pluto.snail

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.pluto.charon.network.interceptors.DebugInterceptor
import com.pluto.snail.app.Snail

open class SnailApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun attachBaseContext(base: Context?) {
        MultiDex.install(base)
        super.attachBaseContext(base)
    }
}

private lateinit var INSTANCE: Application

object AppContext : ContextWrapper(INSTANCE)
