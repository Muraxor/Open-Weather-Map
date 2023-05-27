package com.app.open_weather_map.utils.client

import com.app.open_weather_map.data.network.exceptions.NetworkException
import okhttp3.Response
import kotlin.reflect.KClass

interface NetworkMessageResolver {

    fun resolveServerMessage(response: Response): String

    fun getMessageByException(kClass: KClass<out NetworkException>): String
}
