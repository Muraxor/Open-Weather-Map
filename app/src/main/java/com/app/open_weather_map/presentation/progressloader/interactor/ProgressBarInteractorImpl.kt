package com.app.open_weather_map.presentation.progressloader.interactor

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class ProgressBarInteractorImpl @Inject constructor() : ProgressBarInteractor {

    private val _progressStateFlow = MutableSharedFlow<IsShowing>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    override val progressStateFlow = _progressStateFlow.asSharedFlow()

    override suspend fun send(state: IsShowing) {
        _progressStateFlow.emit(state)
    }

    override fun trySend(state: IsShowing) {
        _progressStateFlow.tryEmit(state)
    }
}
