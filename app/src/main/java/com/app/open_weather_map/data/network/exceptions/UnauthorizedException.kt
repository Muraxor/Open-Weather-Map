package com.app.open_weather_map.data.network.exceptions

import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

data class UnauthorizedException(
    override val code: Int = HTTP_UNAUTHORIZED,
    override val message: String
) : NetworkException()
