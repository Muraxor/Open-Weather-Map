package com.app.open_weather_map.di.modules.android

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class SystemModule {

    @Provides
    @Reusable
    fun provideConnectivityManager(context: Context): ConnectivityManager =
        context.getSystemService(ConnectivityManager::class.java)

    @Provides
    @Reusable
    fun provideLocationManager(context: Context): LocationManager =
        context.getSystemService(LocationManager::class.java)
}
