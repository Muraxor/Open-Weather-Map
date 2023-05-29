package com.app.open_weather_map.data.mappers.weather

import com.app.open_weather_map.data.database.entities.WeatherEntity
import com.app.open_weather_map.data.mappers.Mapper
import com.app.open_weather_map.domain.entities.Weather

object WeatherMapper : Mapper<WeatherEntity, Weather> {

    override fun toDomainModel(input: WeatherEntity): Weather = with(input) {
        Weather(
            id = id,
            main = main,
            description = description,
            icon = icon
        )
    }

    override fun toEntityModel(input: Weather): WeatherEntity = with(input) {
        WeatherEntity(
            id = id,
            main = main,
            description = description,
            icon = icon
        )
    }
}
