package com.pluto.snail.apk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pluto.snail.bus.Xo

const val DOWN_LOAD_SUCCESS = 101001

class ApkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getLongExtra(DownloadService.EXTENDED_DATA_STATUS, -1)!!
        Xo.busToNum(DOWN_LOAD_SUCCESS, id.toInt())
    }
}