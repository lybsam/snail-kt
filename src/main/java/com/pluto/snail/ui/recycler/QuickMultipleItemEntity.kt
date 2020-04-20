package com.pluto.snail.ui.recycler

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import java.util.LinkedHashMap

class QuickMultipleItemEntity(fields: LinkedHashMap<Any, Any>) : MultiItemEntity, Serializable {
    private val ITEM_QUEUE = ReferenceQueue<LinkedHashMap<Any, Any>>()
    private val MULTIPLE_FIELDS = LinkedHashMap<Any, Any>()
    private val FIELDS_REFERENCE = SoftReference(MULTIPLE_FIELDS, ITEM_QUEUE)

    init {
        FIELDS_REFERENCE.get()?.putAll(fields)
    }

    companion object {
        val builder = QuickMultipleBuilder()
    }

    fun <T> getField(key: Any): T {
        return FIELDS_REFERENCE.get()!![key] as T
    }

    fun getFields(): LinkedHashMap<*, *>? {
        return FIELDS_REFERENCE.get()
    }

    fun setField(key: Any, value: Any): QuickMultipleItemEntity {
        FIELDS_REFERENCE.get()!!.put(key, value)
        return this
    }


    fun <T> getContent(): T {
        return getField<T>(QuickMultipleFields.ITEM_CONTENT)
    }

    fun getSpanSize(): Int {
        return getField<Int>(QuickMultipleFields.SPAN_SIZE)
    }


    override val itemType: Int
        get() = FIELDS_REFERENCE.get()?.get(QuickMultipleFields.ITEM_TYPE) as Int
}
