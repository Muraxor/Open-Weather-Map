package com.app.open_weather_map.data.network.response

import com.google.gson.annotations.SerializedName

// FIXME:
@Suppress("unused")
class MainInfo(
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("pressure")
    val pressure: Int
)
