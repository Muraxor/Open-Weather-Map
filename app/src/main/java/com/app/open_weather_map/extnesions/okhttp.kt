package com.app.open_weather_map.extnesions

import com.app.open_weather_map.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient

fun OkHttpClient.Builder.addDebugInterceptor(interceptor: Interceptor) = apply {
    if (BuildConfig.DEBUG) {
        addInterceptor(interceptor)
    }
}
