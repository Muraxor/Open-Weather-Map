package com.app.open_weather_map.domain.repositories

import android.location.Location
import androidx.lifecycle.LiveData
import com.app.open_weather_map.data.database.entities.CityAndWeatherRoom
import com.app.open_weather_map.data.network.response.CityWeatherResponse

interface CityWeatherRepository {

    fun getCityAndWeatherLiveData(cityName: String): LiveData<CityAndWeatherRoom?>

    suspend fun getCityAndWeather(cityName: String): CityAndWeatherRoom?

    suspend fun getNetworkWeather(location: Location): CityWeatherResponse

    suspend fun getNetworkWeather(cityName: String): CityWeatherResponse
}
