package com.app.open_weather_map.data.network.exceptions

/**
 * [java.net.ConnectException]
 */
data class ConnectionRefusedException(
    override val code: Int = NO_CODE,
    override val message: String
) : NetworkException()
