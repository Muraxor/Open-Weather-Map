package com.app.open_weather_map.di.modules.repository

import com.app.open_weather_map.data.repositories.CityRepositoryImpl
import com.app.open_weather_map.data.repositories.CityWeatherRepositoryImpl
import com.app.open_weather_map.domain.repositories.CityRepository
import com.app.open_weather_map.domain.repositories.CityWeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindCityWeatherRepository(impl: CityWeatherRepositoryImpl): CityWeatherRepository

    @Binds
    fun bindCityRepository(impl: CityRepositoryImpl): CityRepository
}
