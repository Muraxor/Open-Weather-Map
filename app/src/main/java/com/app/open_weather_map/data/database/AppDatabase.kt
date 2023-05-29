package com.app.open_weather_map.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.open_weather_map.data.database.dao.city.CityDao
import com.app.open_weather_map.data.database.dao.weather.WeatherDao
import com.app.open_weather_map.data.database.entities.WeatherEntity
import com.app.open_weather_map.data.database.entities.city.CityEntity

@Database(
    entities = [
        CityEntity::class,
        WeatherEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun weatherDao(): WeatherDao
}
