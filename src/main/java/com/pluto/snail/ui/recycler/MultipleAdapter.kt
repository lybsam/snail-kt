package com.pluto.snail.ui.recycler

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter

abstract class MultipleAdapter<T> constructor(entity: List<MultipleItemEntity>) :
    BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>(entity),
    BaseQuickAdapter.SpanSizeLookup {

    init {
        //设置宽度监听
        setSpanSizeLookup(this)
    }

    protected open fun initAnimation() {
        openLoadAnimation()
        //多次执行动画
        isFirstOnly(false)
    }

    override fun createBaseViewHolder(view: View): MultipleViewHolder {
        return MultipleViewHolder.create(view)
    }

    override fun getSpanSize(gridLayoutManager: GridLayoutManager, position: Int): Int {
        return data[position].getField(MultipleFields.SPAN_SIZE)
    }
}