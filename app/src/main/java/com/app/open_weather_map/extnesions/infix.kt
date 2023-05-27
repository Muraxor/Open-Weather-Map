package com.app.open_weather_map.extnesions

import kotlinx.coroutines.Job

inline infix fun <reified T> T.andThen(block: (T) -> Unit) {
    block(this)
}

suspend inline infix fun <reified T : Job> T.joinThen(block: T.() -> Unit) {
    join()
    block()
}
