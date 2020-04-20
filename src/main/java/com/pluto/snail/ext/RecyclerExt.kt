package com.pluto.snail.ext

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.pluto.snail.R
import com.pluto.snail.ui.recycler.BaseDecoration
import com.pluto.snail.ui.recycler.QuickMultipleAdapter


fun <T : QuickMultipleAdapter> RecyclerView.initMultiple(
    mAdapter: T,
    span: Int = 4,
    size: Int = 0,
    color: Int = R.color.white,
    isMore: Boolean = false,
    morelistener: () -> Unit = {}
) {
    val manager = GridLayoutManager(context, span)
    addItemDecoration(BaseDecoration.create(ContextCompat.getColor(context, color), size))
    layoutManager = manager
    adapter = mAdapter
    if (isMore) {
        mAdapter.loadMoreModule.setOnLoadMoreListener({ morelistener.invoke() })
        mAdapter.loadMoreModule.isAutoLoadMore = true
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
    }

}


fun <T> BaseQuickAdapter<T, *>.display(
    list: List<T>,
    page: Int,
    ref: Boolean = false,
    max: Int = 30
) {
    val size = list.size
    if (page == 0 || this.data.size > 30) {
        when (ref) {
            true -> {
                if (size >= max) {
                    this.loadMoreModule.loadMoreComplete()
                } else {
                    this.loadMoreModule.loadMoreEnd(true)
                }
                this.setList(list)
            }
            false -> {
                if (size >= max) {
                    this.loadMoreModule.loadMoreEnd()
                } else {
                    this.loadMoreModule.loadMoreEnd(true)
                }
                this.addData(list)
            }
        }
    } else {
        if (this.data.size >= max) {
            this.loadMoreModule.loadMoreEnd()
        } else {
            this.loadMoreModule.loadMoreEnd(true)
        }
    }
}
