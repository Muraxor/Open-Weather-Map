package com.app.open_weather_map.utils.observers.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun networkStatusFlow(): Flow<Status>

    fun getStatus(): Status

    enum class Status {
        AVAILABLE,
        UNAVAILABLE,
        LOSING,
        LOST
    }
}
