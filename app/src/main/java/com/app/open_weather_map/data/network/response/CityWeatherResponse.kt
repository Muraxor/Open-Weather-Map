package com.app.open_weather_map.data.network.response

import com.app.open_weather_map.domain.entities.Weather
import com.google.gson.annotations.SerializedName

class CityWeatherResponse(
    @SerializedName("name")
    val cityName: String,
    @SerializedName("id")
    val cityId: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val mainInfo: MainInfo
)