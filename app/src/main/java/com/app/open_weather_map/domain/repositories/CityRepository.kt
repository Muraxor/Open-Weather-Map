package com.app.open_weather_map.domain.repositories

import androidx.lifecycle.LiveData
import com.app.open_weather_map.data.database.entities.city.CityEntity
import com.app.open_weather_map.utils.CityName

interface CityRepository {

    suspend fun isFavorite(cityName: CityName): Boolean

    fun isFavoriteLiveData(cityName: CityName): LiveData<Boolean>

    fun getFavoritesLivaData(): LiveData<List<CityEntity>>

    suspend fun markFavorite(cityName: CityName)

    suspend fun unMarkFavorite(cityName: CityName)
}
