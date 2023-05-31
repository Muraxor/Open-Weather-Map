package com.app.open_weather_map.utils

import android.location.Location
import com.app.open_weather_map.domain.repositories.CityWeatherRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class SelectCityMediatorImpl @Inject constructor(
    private val cityWeatherRepository: CityWeatherRepository
) : SelectCityMediator {

    private val _city = MutableSharedFlow<CityName>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1,
        replay = 1
    )
    override val city = _city.asSharedFlow()

    override suspend fun loadPrimaryCity(location: Location) {
        val response = cityWeatherRepository.getNetworkWeather(location)
        pushCityName(response.cityName)
    }

    override suspend fun pushCityName(name: CityName) {
        _city.emit(name)
        cityWeatherRepository.getNetworkWeather(name)
    }
}
