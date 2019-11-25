package com.pluto.snail.ui.recycler

import androidx.annotation.ColorInt
import com.choices.divider.DividerItemDecoration

class BaseDecoration private constructor(@ColorInt color: Int, size: Int) : DividerItemDecoration() {

    init {
        setDividerLookup(DividerLookupImpl(color, size))
    }

    companion object {

        fun create(@ColorInt color: Int, size: Int): BaseDecoration {
            return BaseDecoration(color, size)
        }
    }
}