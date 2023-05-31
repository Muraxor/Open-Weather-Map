package com.app.open_weather_map.utils.network

object ResultWrapper {

    suspend fun <T> wrap(
        block: suspend () -> T
    ): Result<T> {
        return try {
            val response = block()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
