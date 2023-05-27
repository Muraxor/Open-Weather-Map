package com.app.open_weather_map.data.network.exceptions

/**
 * [500..507]
 */
data class ServerErrorException(
    override val code: Int,
    override val message: String
) : NetworkException()
