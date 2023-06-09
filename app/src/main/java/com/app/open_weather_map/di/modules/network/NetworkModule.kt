package com.app.open_weather_map.di.modules.network

import com.app.open_weather_map.BuildConfig
import com.app.open_weather_map.data.network.interceptors.HttpErrorsInterceptor
import com.app.open_weather_map.data.network.interceptors.connection.InternetConnectionInterceptor
import com.app.open_weather_map.di.modules.InterceptorsModule
import com.app.open_weather_map.di.modules.api.ApiModule
import com.app.open_weather_map.extnesions.addDebugInterceptor
import com.google.gson.Gson
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT = 30L

@Module(
    includes = [
        InterceptorsModule::class,
        ApiModule::class
    ]
)
class NetworkModule {

    // TODO: set url
    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("url")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        internetConnectionInterceptor: InternetConnectionInterceptor,
        httpErrorsInterceptor: HttpErrorsInterceptor,
    ): OkHttpClient = with(OkHttpClient.Builder()) {
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(internetConnectionInterceptor)
        addInterceptor(httpErrorsInterceptor)
        addDebugInterceptor(OkHttpProfilerInterceptor())
        addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.HEADERS
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        build()
    }
}
