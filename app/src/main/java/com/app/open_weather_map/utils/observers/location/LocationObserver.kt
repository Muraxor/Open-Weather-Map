package com.app.open_weather_map.utils.observers.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationObserver {

    fun locationFlow(): Flow<Location>

    fun lastKnownLocation(): Location?

    fun isLocationEnabled(): Boolean
}
