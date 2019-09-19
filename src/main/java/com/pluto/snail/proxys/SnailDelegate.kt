package com.pluto.snail.proxys

import com.pluto.snail.ui.alert.LoadingAlert

abstract class SnailDelegate : BaseDelegate() {
    protected lateinit var loading: LoadingAlert
}