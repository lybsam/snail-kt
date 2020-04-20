package com.pluto.snail.ui.recycler

import com.chad.library.adapter.base.entity.MultiItemEntity

class QuickMultipleEntity<T> : MultiItemEntity {
    override var itemType: Int
        private set
    var spanSize: Int

    constructor(itemType: Int, spanSize: Int, content: T?) {
        this.itemType = itemType
        this.spanSize = spanSize
        this.content = content
    }

    constructor(itemType: Int, spanSize: Int) {
        this.itemType = itemType
        this.spanSize = spanSize
    }

    var content: T? = null

}
