package com.app.open_weather_map.di

import android.content.Context

object AppInjector {

    lateinit var appComponent: AppComponent
        private set

    internal fun initComponent(
        applicationContext: Context
    ) {
        if (AppInjector::appComponent.isInitialized.not()) {
            appComponent = DaggerAppComponent
                .builder()
                .context(applicationContext)
                .build()
        }
    }
}
