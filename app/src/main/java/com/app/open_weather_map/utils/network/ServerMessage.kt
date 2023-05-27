package com.app.open_weather_map.utils.network

import com.google.gson.annotations.SerializedName

/**
 * Parse error message from server
 */
data class ServerMessage(
    @SerializedName("message")
    val message: String
)
