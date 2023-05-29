package com.app.open_weather_map.data.mappers

interface RoomMapper<I, O> {

    fun toEntityModel(input: I): O
}
