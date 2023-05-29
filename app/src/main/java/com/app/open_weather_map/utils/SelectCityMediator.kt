package com.app.open_weather_map.utils

import android.location.Location
import kotlinx.coroutines.flow.SharedFlow

typealias CityName = String

interface SelectCityMediator {

    val city: SharedFlow<CityName>

    suspend fun loadPrimaryCity(location: Location)

    suspend fun pushCityName(name: CityName)
}
