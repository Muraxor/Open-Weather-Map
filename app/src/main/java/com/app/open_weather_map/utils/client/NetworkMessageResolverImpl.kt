package com.app.open_weather_map.utils.client

import android.content.Context
import com.app.open_weather_map.R
import com.app.open_weather_map.data.network.exceptions.AbsentNetworkConnectionException
import com.app.open_weather_map.data.network.exceptions.ConnectionRefusedException
import com.app.open_weather_map.data.network.exceptions.NetworkException
import com.app.open_weather_map.data.network.exceptions.ServerTimeoutException
import com.google.gson.Gson
import com.app.open_weather_map.utils.network.ServerMessage
import okhttp3.Response
import javax.inject.Inject
import kotlin.reflect.KClass

class NetworkMessageResolverImpl @Inject constructor(
    private val context: Context
) : NetworkMessageResolver {

    private val gson = Gson()

    private val universalErrorText = context.getString(R.string.something_go_wrong)

    override fun resolveServerMessage(response: Response): String {
        val body = response.body
            ?: return response.message.takeIf { it.isNotEmpty() }
                ?: return universalErrorText

        if (body.contentType()?.type != "application") {
            return universalErrorText
        }

        return gson.fromJson(body.string(), ServerMessage::class.java).message
    }

    override fun getMessageByException(kClass: KClass<out NetworkException>): String {
        val res = when (kClass) {
            ServerTimeoutException::class -> R.string.server_timeout_error_text
            ConnectionRefusedException::class -> R.string.connection_refused_error_text
            AbsentNetworkConnectionException::class -> R.string.internet_connection_error_text
            else -> R.string.something_go_wrong
        }

        return context.getString(res)
    }
}
