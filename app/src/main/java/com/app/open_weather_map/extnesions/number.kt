package com.app.open_weather_map.extnesions

fun Number.isEmpty() = this == 0

fun Number.isNotEmpty() = this != 0

// FIXME: test and rework
inline fun <reified T : Number> T?.orEmpty(): T = 0 as T
