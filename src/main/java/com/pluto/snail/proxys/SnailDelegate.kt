package com.pluto.snail.proxys

import android.os.Bundle
import com.pluto.snail.ui.alert.LoadingAlert

abstract class SnailDelegate : BaseDelegate() {
    lateinit var loading: LoadingAlert

}