package com.app.open_weather_map.domain.repositories

import android.location.Location
import com.app.open_weather_map.data.database.entities.CityAndWeatherRoom
import com.app.open_weather_map.data.network.response.CityWeatherResponse
import kotlinx.coroutines.flow.Flow

interface CityWeatherRepository {

    fun getCityAndWeatherFlow(cityName: String): Flow<CityAndWeatherRoom?>

    suspend fun getCityAndWeather(cityName: String): CityAndWeatherRoom

    suspend fun getNetworkWeather(location: Location): CityWeatherResponse

    suspend fun getNetworkWeather(cityName: String): CityWeatherResponse
}
