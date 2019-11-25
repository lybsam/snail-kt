package com.pluto.snail.ext

import com.pluto.charon.ui.recycler.MultipleEntityBuilder
import com.pluto.charon.ui.recycler.MultipleFields
import com.pluto.charon.ui.recycler.MultipleItemEntity



fun MultipleEntityBuilder.type(v: Int): MultipleEntityBuilder {
    this.setField(MultipleFields.ITEM_TYPE, v)
    return this
}
fun MultipleEntityBuilder.span(v:Int=4): MultipleEntityBuilder {
    this.setField(MultipleFields.SPAN_SIZE, v)
    return this
}


fun MultipleEntityBuilder.id(v: String): MultipleEntityBuilder {
    this.setField(MultipleFields.ID, v)
    return this
}


fun MultipleItemEntity.id():Any{
    return this.getField<String>(MultipleFields.ID)
}


fun MultipleEntityBuilder.content(v: String=""): MultipleEntityBuilder {
    this.setField(MultipleFields.CONTENT, v)
    return this
}

fun MultipleItemEntity.content():String{
    return this.getField(MultipleFields.CONTENT)
}


fun MultipleEntityBuilder.tag(v: Boolean): MultipleEntityBuilder {
    this.setField(MultipleFields.TAG, v)
    return this
}


fun MultipleItemEntity.tag():Boolean{
    return this.getField(MultipleFields.TAG)
}

fun MultipleEntityBuilder.item(v: Any): MultipleEntityBuilder {
    this.setField(MultipleFields.ITEM, v)
    return this
}

fun MultipleItemEntity.item():Any{
    return this.getField(MultipleFields.ITEM)
}


fun MultipleEntityBuilder.time(v: String): MultipleEntityBuilder {
    this.setField(MultipleFields.TIME, v)
    return this
}

fun MultipleItemEntity.time():String{
    return this.getField(MultipleFields.TIME)
}
