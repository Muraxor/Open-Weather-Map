package com.app.open_weather_map.data.mappers

interface Mapper<Entity, Rest> : DomainMapper<Entity, Rest>, RoomMapper<Rest, Entity>
