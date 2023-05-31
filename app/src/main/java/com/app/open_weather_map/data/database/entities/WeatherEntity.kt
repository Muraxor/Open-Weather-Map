package com.app.open_weather_map.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val WeatherTableName = "Weathers"

@Entity(
    tableName = WeatherTableName
)
data class WeatherEntity(
    val main: String?,  //"Drizzle",
    val description: String?,   //"light intensity drizzle",
    val icon: String?,  //"09d"
    @PrimaryKey val cityId: Int,
    val temp: Float,
    val pressure: Int,
    val speed: Float,
    @ColumnInfo(defaultValue = "0")
    val deg: Int,
    @ColumnInfo(defaultValue = "0.0")
    val gust: Float
)
