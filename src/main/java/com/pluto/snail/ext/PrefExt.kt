package com.pluto.snail.ext

import com.pluto.snail.AppContext
import kotlin.reflect.jvm.jvmName


inline fun <reified R, T> R.pref(default: T) = Preference(AppContext, "", default, R::class.jvmName)