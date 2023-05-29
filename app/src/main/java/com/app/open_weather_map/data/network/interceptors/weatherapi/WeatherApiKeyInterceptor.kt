package com.app.open_weather_map.data.network.interceptors.weatherapi

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class WeatherApiKeyInterceptor @Inject constructor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder().addQueryParameter("appid", apiKey).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}