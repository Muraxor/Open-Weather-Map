package com.app.open_weather_map.data.repositories

import android.location.Location
import com.app.open_weather_map.data.api.WeatherApi
import com.app.open_weather_map.data.database.dao.city.CityDao
import com.app.open_weather_map.data.database.dao.weather.WeatherDao
import com.app.open_weather_map.data.database.entities.CityAndWeatherRoom
import com.app.open_weather_map.data.database.entities.city.CityEntity
import com.app.open_weather_map.data.mappers.weather.WeatherMapper
import com.app.open_weather_map.data.network.response.CityWeatherResponse
import com.app.open_weather_map.di.modules.IoDispatcher
import com.app.open_weather_map.domain.repositories.CityWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityWeatherRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val weatherDao: WeatherDao,
    private val api: WeatherApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CityWeatherRepository {

    override fun getCityAndWeatherFlow(cityName: String): Flow<CityAndWeatherRoom?> =
        cityDao.getCityAndWeatherFlow(cityName)

    override suspend fun getCityAndWeather(cityName: String): CityAndWeatherRoom =
        withContext(ioDispatcher) {
            cityDao.getCityAndWeather(cityName).takeIf { it != null }
                ?: throw IllegalStateException()
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

    // FIXME:
    private suspend fun sync(responseBody: CityWeatherResponse) = with(responseBody) {
        val city =
            CityEntity(cityId, weather.first().id, cityName, false)
        cityDao.insert(city)

        val weather = WeatherMapper.toEntityModel(weather.first())
        weatherDao.insert(weather)
    }
}
