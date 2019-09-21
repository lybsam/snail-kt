package com.pluto.snail.ext

import android.annotation.SuppressLint
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.pluto.snail.R
import com.pluto.charon.ui.recycler.*

@SuppressLint("WrongConstant")
fun RecyclerView.initLinear(
    size: Int = 0,
    color: Int = R.color.p_bg,
    or: Int = LinearLayout.VERTICAL
) {
    val manager = LinearLayoutManager(context)
    manager.orientation = or
    layoutManager = manager
    addItemDecoration(BaseDecoration.create(ContextCompat.getColor(context, color), size))
}


@SuppressLint("WrongConstant")
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


fun <T> BaseQuickAdapter<T, *>.display(
    list: ArrayList<T>,
    page: Int,
    ref: Boolean = false,
    max: Int = 30
) {
    val size = list.size
    if (page == 0 || this.data.size > 30) {
        when (ref) {
            true -> {
                if (size >= max) {
                    this.loadMoreComplete()
                } else {
                    this.loadMoreEnd(true)
                }
                this.setNewData(list)
            }
            false -> {
                if (size >= max) {
                    this.loadMoreEnd()
                } else {
                    this.loadMoreEnd(true)
                }
                this.addData(list)
            }
        }
    } else {
        if (this.data.size >= max) {
            this.loadMoreEnd()
        } else {
            this.loadMoreEnd(true)
        }
    }
    this.disableLoadMoreIfNotFullPage()
}
