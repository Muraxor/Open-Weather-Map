package com.app.open_weather_map.di.modules.room

import com.app.open_weather_map.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DaoModule {

    @Provides
    fun provideCityDao(db: AppDatabase) = db.cityDao()

    @Provides
    fun provideWeatherDao(db: AppDatabase) = db.weatherDao()
}
