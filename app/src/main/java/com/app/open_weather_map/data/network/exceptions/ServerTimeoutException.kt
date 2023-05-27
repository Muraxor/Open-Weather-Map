package com.app.open_weather_map.data.network.exceptions

/**
 * SocketTimeoutException
 */
data class ServerTimeoutException(
    override val code: Int = NO_CODE,
    override val message: String
) : NetworkException()
