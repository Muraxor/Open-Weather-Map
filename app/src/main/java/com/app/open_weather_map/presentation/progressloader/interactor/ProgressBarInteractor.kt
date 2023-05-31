package com.app.open_weather_map.presentation.progressloader.interactor

import kotlinx.coroutines.flow.SharedFlow

typealias IsShowing = Boolean

interface ProgressBarInteractor {

    val progressStateFlow: SharedFlow<IsShowing>

    suspend fun send(state: IsShowing)

    fun trySend(state: IsShowing)
}
