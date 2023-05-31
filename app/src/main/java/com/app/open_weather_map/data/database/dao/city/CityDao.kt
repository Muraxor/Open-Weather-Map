package com.app.open_weather_map.data.database.dao.city

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.app.open_weather_map.data.database.dao.BaseDao
import com.app.open_weather_map.data.database.entities.CityAndWeatherRoom
import com.app.open_weather_map.data.database.entities.city.CityEntity
import com.app.open_weather_map.data.database.entities.city.CityTableName
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao : BaseDao<CityEntity> {

    @Query("SELECT isFavorite FROM $CityTableName WHERE name = :cityName LIMIT 1")
    suspend fun isFavorite(cityName: String): Boolean

    @Transaction
    @Query("SELECT * FROM $CityTableName WHERE name = :cityName")
    fun isFavoriteLiveData(cityName: String): LiveData<List<CityEntity>>

    @Transaction
    @Query("SELECT * FROM $CityTableName WHERE name LIKE :searchQuery")
    fun searchCitiesByName(searchQuery: String): Flow<List<CityEntity>>

    @Query("UPDATE $CityTableName SET isFavorite = :flag WHERE name = :cityName")
    suspend fun markFavorite(cityName: String, flag: Boolean)

    @Query("UPDATE $CityTableName SET isFavorite = :flag WHERE name = :cityName")
    suspend fun unMarkFavorite(cityName: String, flag: Boolean)

    @Query("SELECT * from $CityTableName WHERE isFavorite = 1")
    fun getFavoritesFlow(): Flow<List<CityEntity>>

    @Transaction
    @Query("SELECT * from $CityTableName WHERE name = :cityName")
    fun getCityAndWeatherFlow(cityName: String): Flow<CityAndWeatherRoom?>

    @Query("DELETE FROM $CityTableName WHERE id = :cityId")
    fun delete(cityId: Int)
}
