package com.app.open_weather_map.data.network.exceptions

data class AbsentNetworkConnectionException(
    override val message: String,
    override val code: Int = NO_CODE
): NetworkException()
