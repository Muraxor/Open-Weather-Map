package com.app.open_weather_map.utils.observers.httperrors

import com.app.open_weather_map.data.network.exceptions.NetworkException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class HttpErrorManagerImpl @Inject constructor() : HttpErrorManager {

    private val _httpExceptions = MutableSharedFlow<NetworkException>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    override val httpExceptions = _httpExceptions.asSharedFlow()

    override fun send(e: NetworkException) {
        _httpExceptions.tryEmit(e)
    }
}
