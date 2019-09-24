package com.pluto.snail.ui.alert

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.ybq.android.spinkit.SpriteFactory
import com.github.ybq.android.spinkit.Style
import com.pluto.snail.R
import com.pluto.snail.ext.analysis
import com.pluto.snail.proxys.SnailDelegate
import kotlinx.android.synthetic.main.alert_loding.*

class LoadingAlert : BaseAlert() {
    override fun layout(): Int {
        return R.layout.alert_loding
    }

    override fun init() {
        analysis("style", "color") {
            val count = it[0].toString().toInt()
            val style = Style.values()[count]
            val create = SpriteFactory.create(style)
            spin_kit.setIndeterminateDrawable(create)
        }
    }


    fun close() {
        dismiss()
    }
}


fun AppCompatActivity.loading(style: Int = Style.CHASING_DOTS.ordinal): LoadingAlert {
    val loading = LoadingAlert()
    val bundle = Bundle()
    bundle.putInt("style", style)
    loading.arguments = bundle
    loading.show(this.supportFragmentManager, "loading")
    return loading
}

fun SnailDelegate.loading(style: Int = Style.CHASING_DOTS.ordinal): LoadingAlert {
    val loading = LoadingAlert()
    val bundle = Bundle()
    bundle.putInt("style", style)
    loading.arguments = bundle
    loading.show(this.fragmentManager!!, "loading")
    return loading
}
