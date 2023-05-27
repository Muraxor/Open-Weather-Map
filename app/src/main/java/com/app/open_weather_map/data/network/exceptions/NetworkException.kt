package com.app.open_weather_map.data.network.exceptions

import okio.IOException

sealed class NetworkException : IOException() {

    abstract val code: Int

    abstract override val message: String

    companion object {

        const val NO_CODE = -1
    }
}
