package com.pluto.snail.ext

import com.pluto.snail.util.MD5Util
import java.text.SimpleDateFormat
import java.util.*


//解析时间戳
fun String.timeStamp(): String {
    val format = "yyyy-MM-dd HH:mm:ss"
    val sdf = SimpleDateFormat(format)
    return sdf.format(Date("${this}000".toLong()))
}


//md5
fun String.toMD5(): String {
    return MD5Util.getInstance().getMD5ofStr(this)
}