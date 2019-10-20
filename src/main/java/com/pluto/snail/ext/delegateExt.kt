package com.pluto.snail.ext

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.pluto.snail.proxys.SnailDelegate

fun <T : SnailDelegate, K : SnailDelegate> T.start(delegate: K, vararg args: Pair<String, Any>) {
    val bundle = Bundle()
    val data = HashMap<String, Any>()
    if (args.isNotEmpty()) {
        data.putAll(args)
        data.forEach {
            when (it.value) {
                is Int -> bundle.putInt(it.key, (it.value as Int))
                is String -> bundle.putString(it.key, (it.value as String))
                is Boolean -> bundle.putBoolean(it.key, (it.value as Boolean))
            }
        }
        delegate.arguments = bundle
    }
    this.supportDelegate.hideSoftInput()
    this.supportDelegate.start(delegate)
}


fun Fragment.analysis(vararg keys: String, block: (ArrayList<Any>) -> Unit) {
    val list = arrayListOf<Any>()
    this.arguments?.let { bundle ->
        keys.forEach {
            bundle.get(it)?.let { it1 -> list.add(it1) }
        }
    }
    block.invoke(list)
}


fun <T : SnailDelegate> T.pop() {
    this.supportDelegate.pop()
}


fun SnailDelegate.popTo(delegate: SnailDelegate) {
    this.supportDelegate.popTo(delegate.javaClass, true)
}
