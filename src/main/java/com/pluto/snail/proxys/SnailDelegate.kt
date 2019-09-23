package com.pluto.snail.proxys

import com.pluto.snail.ui.alert.LoadingAlert

abstract class SnailDelegate : BaseDelegate() {
    lateinit var loading: LoadingAlert

    open fun snail(): SnailDelegate {
        return this
    }
}