package com.app.open_weather_map.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val WeatherTableName = "Weathers"

@Entity(
    tableName = WeatherTableName
)
data class WeatherEntity(
    @PrimaryKey val id: Int,
    val main: String,//"Drizzle",
    val description: String,//"light intensity drizzle",
    val icon: String, //"09d"
)
