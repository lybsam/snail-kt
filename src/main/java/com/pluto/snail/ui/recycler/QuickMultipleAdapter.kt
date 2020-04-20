package com.pluto.snail.ui.recycler

import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.listener.GridSpanSizeLookup
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class QuickMultipleAdapter<T>(list: MutableList<QuickMultipleEntity<T>>) :
    BaseMultiItemQuickAdapter<QuickMultipleEntity<T>, BaseViewHolder>(list), GridSpanSizeLookup {
    init {
        setGridSpanSizeLookup(this)
    }

    override fun getSpanSize(
        gridLayoutManager: GridLayoutManager,
        viewType: Int,
        position: Int
    ): Int {
        return data[position].spanSize
    }
}