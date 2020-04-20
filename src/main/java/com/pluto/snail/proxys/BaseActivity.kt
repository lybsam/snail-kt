package com.pluto.snail.proxys

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pluto.snail.app.Snail
import com.pluto.snail.ext.barTandBlack

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout())
        Snail.snail().addActivity(this)
        barTandBlack(true)
        init()
    }

    abstract fun layout(): Int

    abstract fun init()
}