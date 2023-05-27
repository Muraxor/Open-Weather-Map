package com.app.open_weather_map

import android.app.Application
import com.app.open_weather_map.di.AppInjector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDaggerAppComponent()
    }

    private fun initDaggerAppComponent() {
        AppInjector.initComponent(applicationContext)
    }
}
