package com.app.open_weather_map.base.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.open_weather_map.presentation.progressloader.interactor.ProgressBarInteractor
import com.app.open_weather_map.utils.network.ResultWrapper
import kotlinx.coroutines.*
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject lateinit var resultWrapper: ResultWrapper
    @Inject lateinit var progressBarInteractor: ProgressBarInteractor

    private val _error = MutableLiveData<Throwable>()
    @Suppress("unused")
    val error: LiveData<Throwable> = _error

    protected open val tag: String = this::class.java.simpleName

    private val handler = CoroutineExceptionHandler { _, throwable ->
        _error.value = throwable
    }

    internal suspend fun <T> getResult(
        block: suspend () -> T,
    ): Result<T> =
        try {
            progressBarInteractor.send(true)
            resultWrapper.wrap(block)
        } catch (e: Exception) {
            Log.d(tag, (e.stackTraceToString()))
            if (e is CancellationException) {
                throw e
            }
            _error.postValue(e)
            Result.failure(e)
        } finally {
            progressBarInteractor.send(false)
        }

    internal suspend fun <T> wrapWithProgressBar(block: suspend () -> T) {
        try {
            progressBarInteractor.send(true)
            block.invoke()
        } finally {
            progressBarInteractor.send(false)
        }
    }

    protected val viewModelScopeWithHandler
        get() = viewModelScope + handler
}
