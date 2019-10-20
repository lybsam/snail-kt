package com.pluto.snail.bus

import com.alibaba.fastjson.JSON

class XoManage private constructor() {
    private val CALLBACKS = hashMapOf<Int, IXoListener<*>>()

    fun <T> subscribe(type: Int, listener: IXoListener<T>) {
        CALLBACKS[type] = listener
    }


    fun <T> execute(type: Int, data: T) {
        val block = CALLBACKS[type]
        if (block != null) {
            (block as IXoListener<T>).execute(data)
        }
    }


    companion object {
        val bus = XoManage()
    }
}


interface IXoListener<T> {
    fun execute(data: T)
}


object Xo {


    fun subsToSrt(type: Int, block: (String) -> Unit): Xo {
        XoManage.bus.subscribe(type, object : IXoListener<String> {
            override fun execute(data: String) {
                block.invoke(data)
            }
        })
        return this
    }

    fun subsToNum(type: Int, block: (Int) -> Unit): Xo {
        XoManage.bus.subscribe(type, object : IXoListener<Int> {
            override fun execute(data: Int) {
                block.invoke(data)
            }
        })

        return this
    }

    fun subsToNot(type: Int, block: () -> Unit): Xo {
        XoManage.bus.subscribe(type, object : IXoListener<String> {
            override fun execute(data: String) {
                block.invoke()
            }
        })
        return this
    }

    fun subsTobool(type: Int, block: (Boolean) -> Unit): Xo {
        XoManage.bus.subscribe(type, object : IXoListener<Boolean> {
            override fun execute(data: Boolean) {
                block.invoke(data)
            }
        })
        return this
    }

    fun busToStr(type: Int, data: String) {
        XoManage.bus.execute<String>(type, data)
    }

    fun busToJson(type: Int, data: Any) {
        val str = JSON.toJSONString(data)
        XoManage.bus.execute<String>(type, str)
    }

    fun busToNum(type: Int, data: Int) {
        XoManage.bus.execute<Int>(type, data)
    }

    fun busTobool(type: Int, data: Boolean) {
        XoManage.bus.execute<Boolean>(type, data)
    }

    fun busToNot(type: Int) {
        XoManage.bus.execute<String>(type, "")
    }
}
