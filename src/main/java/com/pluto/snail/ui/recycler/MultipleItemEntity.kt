package  com.pluto.snail.ui.recycler

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import java.util.*


class MultipleItemEntity(fields: LinkedHashMap<Any, Any>):MultiItemEntity,Serializable{

    private val ITEM_QUEUE = ReferenceQueue<LinkedHashMap<Any, Any>>()
    private val MULTIPLE_FIELDS = LinkedHashMap<Any, Any>()
    private val FIELDS_REFERENCE = SoftReference(MULTIPLE_FIELDS, ITEM_QUEUE)
    init {
        FIELDS_REFERENCE.get()?.putAll(fields)
    }

    companion object {
        val builder = MultipleEntityBuilder()
    }

    override fun getItemType(): Int {
        return FIELDS_REFERENCE.get()?.get(MultipleFields.ITEM_TYPE) as Int
    }

    fun <T> getField(key: Any): T {
        return FIELDS_REFERENCE.get()!![key] as T
    }

    fun getFields(): LinkedHashMap<*, *>? {
        return FIELDS_REFERENCE.get()
    }

    fun setField(key: Any, value: Any): MultipleItemEntity {
        FIELDS_REFERENCE.get()!!.put(key, value)
        return this
    }
}