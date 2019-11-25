package com.pluto.snail.ui.recycler

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder

class MultipleViewHolder private constructor(view: View) : BaseViewHolder(view) {
    companion object {
        fun create(view: View): MultipleViewHolder {
            return MultipleViewHolder(view)
        }
    }
}
