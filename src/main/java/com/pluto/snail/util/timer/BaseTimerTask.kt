package com.pluto.charon.util.timer

import java.util.*

class BaseTimerTask(timerListener: ITimerListener) : TimerTask() {
    private val mITimerListener: ITimerListener = timerListener
    override fun run() {
        mITimerListener.onTimer()
    }
}