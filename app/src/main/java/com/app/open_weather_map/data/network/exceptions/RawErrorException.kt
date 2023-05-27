package com.app.open_weather_map.data.network.exceptions

data class RawErrorException(
    override val code: Int,
    override val message: String
) : NetworkException()
