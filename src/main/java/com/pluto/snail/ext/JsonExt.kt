package com.pluto.charon.ext

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject


fun String.toJsArr(): JSONArray {
    return JSON.parseArray(this)
}

fun String.Json(): JSONObject {
    return JSON.parseObject(this)
}


fun jsSrt(vararg params: Pair<String, Any>): String {
    params.isNotEmpty().yes {
        val data = HashMap<Any, Any>()
        data.putAll(params)
        return JSON.toJSONString(data)
    }.otherwise {
        return ""
    }
}

fun String.data(): JSONObject {
    return JSON.parseObject(this).getJSONObject("data")
}


fun JSONObject.toJsObj(key: String): JSONObject {
    return this.getJSONObject(key)
}

fun String.code(): Int {
    return JSON.parseObject(this).getIntValue("code")
}

fun String.toJsStr(key: String): String {
    return this.Json().toJsStr(key)
}


fun JSONObject.toJsArr(key: String): ArrayList<JSONObject> {
    val list = arrayListOf<JSONObject>()
    val arr = this.getJSONArray(key)
    if (arr != null) {
        arr.forEach { list.add(it as JSONObject) }
    }
    return list
}


fun JSONObject.toArray(key: String): ArrayList<Any> {
    val list = arrayListOf<Any>()
    val arr = this.getJSONArray(key)
    if (arr != null) {
        arr.forEach { list.add(it) }
    }
    return list
}


fun JSONObject.toJsStr(key: String): String {
    return this.getString(key)
}

fun JSONObject.toJsInt(key: String): Int {
    return this.getIntValue(key)
}