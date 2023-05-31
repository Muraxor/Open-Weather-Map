package com.app.open_weather_map.data.repositories

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.app.open_weather_map.data.api.WeatherApi
import com.app.open_weather_map.data.database.dao.city.CityDao
import com.app.open_weather_map.data.database.dao.weather.WeatherDao
import com.app.open_weather_map.data.database.entities.CityAndWeatherRoom
import com.app.open_weather_map.data.network.response.CityWeatherResponse
import com.app.open_weather_map.data.network.response.CityWeatherResponse.Companion.toEntityModel
import com.app.open_weather_map.di.modules.IoDispatcher
import com.app.open_weather_map.domain.repositories.CityWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityWeatherRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val weatherDao: WeatherDao,
    private val api: WeatherApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CityWeatherRepository {

    override fun getCityAndWeatherLiveData(cityName: String): LiveData<CityAndWeatherRoom?> =
        cityDao.getCityAndWeatherFlow(cityName).asLiveData()

    override suspend fun getCityAndWeather(cityName: String): CityAndWeatherRoom? =
        withContext(ioDispatcher) {
            cityDao.getCityAndWeather(cityName)
        }

    override suspend fun getNetworkWeather(location: Location): CityWeatherResponse =
        withContext(ioDispatcher) {
            api.getWeatherByLocation(lat = location.latitude, lon = location.longitude).also {
                sync(it)
            }
        }

    override suspend fun getNetworkWeather(cityName: String): CityWeatherResponse =
        withContext(ioDispatcher) {
            api.getWeatherByCity(cityName).also {
                sync(it)
            }
        }

    private suspend fun sync(responseBody: CityWeatherResponse) =
        with(responseBody.toEntityModel()) {
            cityDao.insert(city)
            weatherDao.upsert(weather)
        }
}
