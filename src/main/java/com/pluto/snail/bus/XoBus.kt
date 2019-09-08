package com.pluto.charon.bus

object XoBus {
    lateinit var block: (Int, Any) -> Unit
    fun onListener(block: (Int, Any) -> Unit) {
        XoBus.block = block
    }
}