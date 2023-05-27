package com.app.open_weather_map.utils.network

import okio.IOException
import javax.inject.Inject

class ResultWrapper @Inject constructor() {

    suspend fun <T> wrap(
        block: suspend () -> T
    ): Result<T> {
        return try {
            val response = block()
            Result.success(response)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}
