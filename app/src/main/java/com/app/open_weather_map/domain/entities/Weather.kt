package com.app.open_weather_map.domain.entities

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,//"Drizzle",
    @SerializedName("description")
    val description: String,//"light intensity drizzle",
    @SerializedName("icon")
    val icon: String, //"09d"
)
