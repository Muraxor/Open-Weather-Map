package com.app.open_weather_map.data.network.response

import com.app.open_weather_map.data.database.entities.CityAndWeatherRoom
import com.app.open_weather_map.data.database.entities.WeatherEntity
import com.app.open_weather_map.data.database.entities.city.CityEntity
import com.app.open_weather_map.data.mappers.RoomMapper
import com.app.open_weather_map.domain.entities.Weather
import com.google.gson.annotations.SerializedName

class CityWeatherResponse(
    @SerializedName("name") val cityName: String,
    @SerializedName("id") val cityId: Int,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val mainInfo: MainInfo,
    @SerializedName("wind") val wind: Wind
) {

    companion object : RoomMapper<CityWeatherResponse, CityAndWeatherRoom> {

        override fun CityWeatherResponse.toEntityModel(): CityAndWeatherRoom = run {
            val weatherInfo = weather.firstOrNull()

            val weatherEntity = WeatherEntity(
                cityId = cityId,
                deg = wind.deg,
                gust = wind.gust,
                speed = wind.speed,
                temp = mainInfo.temp,
                pressure = mainInfo.pressure,
                description = weatherInfo?.description,
                icon = weatherInfo?.icon,
                main = weatherInfo?.main,
            )

            val cityEntity = CityEntity(cityId, cityName, false)

            CityAndWeatherRoom(cityEntity, weatherEntity)
        }
    }
}
