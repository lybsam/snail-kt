package  com.pluto.charon.ui.recycler

import com.choices.divider.Divider
import com.choices.divider.DividerItemDecoration

class DividerLookupImpl(val colol: Int, val size: Int) : DividerItemDecoration.DividerLookup {

    override fun getHorizontalDivider(position: Int): Divider {
        return Divider.Builder()
                .size(size)
                .color(colol)
                .build()
    }

    override fun getVerticalDivider(position: Int): Divider {
        return Divider.Builder()
                .size(size)
                .color(colol)
                .build()
    }

}