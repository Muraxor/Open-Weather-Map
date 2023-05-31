package com.app.open_weather_map.data.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Upsert

interface BaseDao<E> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: E): Long

    @Upsert
    suspend fun upsert(entity: E): Long
}
