package com.app.open_weather_map.data.api

import com.app.open_weather_map.data.network.response.CityWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String
    ): CityWeatherResponse

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): CityWeatherResponse
}
