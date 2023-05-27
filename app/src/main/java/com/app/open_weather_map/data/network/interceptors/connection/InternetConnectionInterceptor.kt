package com.app.open_weather_map.data.network.interceptors.connection

import com.app.open_weather_map.data.network.exceptions.AbsentNetworkConnectionException
import com.app.open_weather_map.utils.client.NetworkMessageResolver
import com.app.open_weather_map.utils.observers.httperrors.HttpErrorManager
import com.app.open_weather_map.utils.observers.network.ConnectivityObserver
import okhttp3.Interceptor
import okhttp3.Response

class InternetConnectionInterceptor(
    private val httpErrorManager: HttpErrorManager,
    private val networkMessageResolver: NetworkMessageResolver,
    private val connectivityObserver: ConnectivityObserver
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val isNetworkActive =
            connectivityObserver.getStatus() == ConnectivityObserver.Status.AVAILABLE

        return if (isNetworkActive) {
            chain.proceed(chain.request())
        } else {
            val message =
                networkMessageResolver.getMessageByException(AbsentNetworkConnectionException::class)

            throw AbsentNetworkConnectionException(message).also { e ->
                httpErrorManager.send(e)
            }
        }
    }
}
