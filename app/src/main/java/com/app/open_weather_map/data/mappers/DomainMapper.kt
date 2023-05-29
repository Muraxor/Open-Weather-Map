package com.app.open_weather_map.data.mappers

interface DomainMapper<I, O> {

    fun toDomainModel(input: I): O
}
