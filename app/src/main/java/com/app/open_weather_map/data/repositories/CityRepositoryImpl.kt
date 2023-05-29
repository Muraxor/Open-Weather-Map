package com.app.open_weather_map.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.app.open_weather_map.data.database.dao.city.CityDao
import com.app.open_weather_map.data.database.entities.city.CityEntity
import com.app.open_weather_map.di.modules.IoDispatcher
import com.app.open_weather_map.domain.repositories.CityRepository
import com.app.open_weather_map.utils.CityName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CityRepository {

    override suspend fun isFavorite(cityName: CityName): Boolean = withContext(ioDispatcher) {
        cityDao.isFavorite(cityName)
    }

    override fun isFavoriteLiveData(cityName: CityName): LiveData<Boolean> =
        cityDao.isFavoriteLiveData(cityName).map { list ->
            list.find { it.name == cityName }?.isFavorite ?: false
        }

    override fun getFavoritesLivaData(): LiveData<List<CityEntity>> =
        cityDao.getFavoritesFlow().asLiveData()

    override suspend fun markFavorite(cityName: CityName) = withContext(ioDispatcher) {
        cityDao.markFavorite(cityName, true)
    }

    override suspend fun unMarkFavorite(cityName: CityName) = withContext(ioDispatcher) {
        cityDao.unMarkFavorite(cityName, false)
    }
}
