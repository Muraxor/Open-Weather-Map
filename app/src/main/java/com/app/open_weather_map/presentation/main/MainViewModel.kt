package com.app.open_weather_map.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.open_weather_map.base.viewmodel.BaseViewModel
import com.app.open_weather_map.data.network.exceptions.NetworkException
import com.app.open_weather_map.presentation.progressloader.interactor.IsShowing
import com.app.open_weather_map.presentation.progressloader.interactor.ProgressBarInteractor
import com.app.open_weather_map.utils.observers.httperrors.HttpErrorManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val httpErrorManager: HttpErrorManager,
    private val uiStateInteractor: ProgressBarInteractor
) : BaseViewModel() {

    private val _httpExceptionsLiveData = MutableLiveData<NetworkException>()
    val httpExceptionsLiveData: LiveData<NetworkException> = _httpExceptionsLiveData

    private val _progressBarStateLiveData = MutableLiveData<IsShowing>()
    val progressBarStateLiveData: LiveData<IsShowing> = _progressBarStateLiveData

    init {
        observeHttpErrors()
        observeProgressBarState()
    }

    private fun observeHttpErrors() = viewModelScope.launch {
        httpErrorManager.httpExceptions.collect { exception ->
            _httpExceptionsLiveData.postValue(exception)
        }
    }

    private fun observeProgressBarState() {
        viewModelScope.launch {
            uiStateInteractor.progressStateFlow.collect { isShowing ->
                _progressBarStateLiveData.postValue(isShowing)
            }
        }
    }
}
