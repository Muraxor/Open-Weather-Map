package com.app.open_weather_map.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<E> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: E)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entities: List<E>)

    @Update
    suspend fun update(entity: E)

    @Update
    suspend fun update(entities: List<E>)

    @Delete
    suspend fun delete(entity: E)

    @Delete
    suspend fun delete(entities: List<E>)
}
