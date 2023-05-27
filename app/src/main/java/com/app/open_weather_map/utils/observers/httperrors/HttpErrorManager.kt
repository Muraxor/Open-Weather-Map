package com.app.open_weather_map.utils.observers.httperrors

import com.app.open_weather_map.data.network.exceptions.NetworkException
import kotlinx.coroutines.flow.SharedFlow

interface HttpErrorManager {

    val httpExceptions: SharedFlow<NetworkException>

    fun send(e: NetworkException)
}
