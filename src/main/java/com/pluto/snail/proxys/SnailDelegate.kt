package com.pluto.snail.proxys

import com.pluto.snail.ui.alert.LoadingAlert

abstract class SnailDelegate : BaseDelegate() {
    lateinit var loading: LoadingAlert
    fun <T : SnailDelegate> snail(): T {
        return parentFragment as T
    }
}