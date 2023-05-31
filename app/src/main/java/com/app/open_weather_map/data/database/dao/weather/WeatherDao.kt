package com.app.open_weather_map.data.database.dao.weather

import androidx.room.Dao
import androidx.room.Query
import com.app.open_weather_map.data.database.dao.BaseDao
import com.app.open_weather_map.data.database.entities.WeatherEntity
import com.app.open_weather_map.data.database.entities.WeatherTableName

@Dao
interface WeatherDao : BaseDao<WeatherEntity> {

    @Query("SELECT * from $WeatherTableName")
    suspend fun getAll(): List<WeatherEntity>
}
