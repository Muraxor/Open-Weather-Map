package com.app.open_weather_map.data.database.entities.city

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CityTableName = "Cities"

@Entity(
    tableName = CityTableName
)
data class CityEntity(
    @PrimaryKey val id: Int,
    val weatherId: Int,
    val name: String,
    @ColumnInfo(defaultValue = "0")
    val isFavorite: Boolean
)
