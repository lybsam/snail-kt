package com.pluto.snail.ext

import android.annotation.SuppressLint
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.pluto.snail.R
import com.pluto.charon.ui.recycler.*

@SuppressLint("WrongConstant")
fun RecyclerView.initLinear(size: Int = 0, color: Int = R.color.p_bg, or: Int = LinearLayout.VERTICAL) {
    val manager = LinearLayoutManager(context)
    manager.orientation = or
    layoutManager = manager
    addItemDecoration(BaseDecoration.create(ContextCompat.getColor(context, color), size))
}


fun RecyclerView.initVPage(or: Int = LinearLayout.HORIZONTAL) {
    val manager = LinearLayoutManager(context)
    manager.orientation = or
    layoutManager = manager
    val helper = PagerSnapHelper()
    this.setOnFlingListener(null);
    helper.attachToRecyclerView(this)
}


fun RecyclerView.initMultiple(span: Int, size: Int = 0, color: Int = R.color.white) {
    val manager = GridLayoutManager(context, span)
    addItemDecoration(BaseDecoration.create(ContextCompat.getColor(context, color), size))
    layoutManager = manager
}

