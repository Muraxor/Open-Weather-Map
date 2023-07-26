package com.app.open_weather_map.domain.repositories

import androidx.lifecycle.LiveData
import com.app.open_weather_map.data.database.entities.city.CityEntity
import com.app.open_weather_map.utils.CityName
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    suspend fun isFavorite(cityName: CityName): Boolean

    fun isFavoriteLiveData(cityName: CityName): LiveData<Boolean>

    fun searchCitiesByName(searchQuery: String): Flow<List<CityEntity>>

    fun getFavoritesLivaData(): LiveData<List<CityEntity>>

    suspend fun markFavorite(cityName: CityName)

    suspend fun unMarkFavorite(cityName: CityName)
}