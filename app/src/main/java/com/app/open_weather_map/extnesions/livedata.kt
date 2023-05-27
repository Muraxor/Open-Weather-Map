package com.app.open_weather_map.extnesions

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.valueOrThrow(): T {
    return value ?: throw IllegalStateException("Live data does not have some value")
}
