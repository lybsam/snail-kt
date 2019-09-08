package  com.pluto.charon.ui.recycler

import java.util.*


class MultipleEntityBuilder {
    private val FIELDS = LinkedHashMap<Any, Any>()
    init {
        FIELDS.clear()
    }

    fun setItemType(itemType: Int): MultipleEntityBuilder {
        FIELDS[MultipleFields.ITEM_TYPE] = itemType
        return this
    }

    fun setField(key: Any, value: Any): MultipleEntityBuilder {
        FIELDS[key] = value
        return this
    }

    fun setFields(map: LinkedHashMap<*, *>): MultipleEntityBuilder {
        FIELDS.putAll(map)
        return this
    }

    fun build(): MultipleItemEntity {
        return MultipleItemEntity(FIELDS)
    }
}