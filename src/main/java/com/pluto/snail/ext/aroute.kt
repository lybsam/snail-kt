package com.pluto.snail.ext

import com.alibaba.android.arouter.launcher.ARouter
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

fun start(url: String) {
    ARouter.getInstance()
        .build(url)
        .navigation()
}


fun start(url: String, vararg params: Pair<String, Any>) {
    val aRouter = ARouter.getInstance().build(url)
    params.forEach {
        when(it.second){
            is Int->aRouter.withInt(it.first,it.second as Int)
            is String ->aRouter.withString(it.first,it.second as String)
            is Boolean->aRouter.withBoolean(it.first,it.second as Boolean)
            is JvmType.Object->aRouter.withObject(it.first,it.second)
        }
    }
    aRouter.navigation()
}
