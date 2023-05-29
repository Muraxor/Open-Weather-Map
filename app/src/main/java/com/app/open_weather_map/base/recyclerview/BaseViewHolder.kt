package com.app.open_weather_map.base.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<M : Any>(view: View) :
    RecyclerView.ViewHolder(view) {

    @JvmField
    val viewContext: Context = view.context

    abstract fun bind(model: M)

    open fun bind(model: M, payload: List<Any>) {}

    open fun unbind() {}
}
