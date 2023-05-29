package com.app.open_weather_map.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.app.open_weather_map.data.database.entities.city.CityEntity

class CityAndWeatherRoom(
    @Embedded
    val city: CityEntity,
    @Relation(
        parentColumn = "weatherId",
        entityColumn = "id",
        entity = WeatherEntity::class
    )
    val weather: WeatherEntity
)
