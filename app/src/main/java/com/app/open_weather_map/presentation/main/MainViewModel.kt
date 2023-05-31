package com.app.open_weather_map.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.open_weather_map.base.viewmodel.BaseViewModel
import com.app.open_weather_map.data.network.exceptions.NetworkException
import com.app.open_weather_map.presentation.progressloader.interactor.IsShowing
import com.app.open_weather_map.presentation.progressloader.interactor.ProgressBarInteractor
import com.app.open_weather_map.utils.SelectCityMediator
import com.app.open_weather_map.utils.observers.httperrors.HttpErrorManager
import com.app.open_weather_map.utils.observers.location.LocationObserver
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val httpErrorManager: HttpErrorManager,
    private val locationObserver: LocationObserver,
    private val selectCityMediator: SelectCityMediator,
    private val progressInteractor: ProgressBarInteractor
) : BaseViewModel(progressInteractor) {

    private val _httpExceptionsLiveData = MutableLiveData<NetworkException>()
    val httpExceptionsLiveData: LiveData<NetworkException> = _httpExceptionsLiveData

    private val _progressBarStateLiveData = MutableLiveData<IsShowing>()
    val progressBarStateLiveData: LiveData<IsShowing> = _progressBarStateLiveData

    init {
        observeHttpErrors()
        observeProgressBarState()
    }

    internal fun getGpsLocation() {
        viewModelScopeWithHandler.launch {
            wrapWithProgressBar {
                if (locationObserver.isLocationEnabled()) {
                    try {
                        withTimeout(LOCATION_LISTENING_TIMEOUT) {
                            locationObserver.locationFlow().first()
                        }.also {
                            selectCityMediator.loadPrimaryCity(it)
                        }
                    } catch (e: TimeoutCancellationException) {
                        getLastLocation()
                    }
                } else {
                    getLastLocation()
                }
            }
        }
    }

    internal fun getLastLocation() {
        viewModelScopeWithHandler.launch {
            val location = locationObserver.lastKnownLocation()
            if (location != null) {
                selectCityMediator.loadPrimaryCity(location)
            } else {
                throw IllegalStateException("Location is unavailable. Please turn on gps and rerun application")
            }
        }
    }

    private fun observeHttpErrors() = viewModelScope.launch {
        httpErrorManager.httpExceptions.collect { exception ->
            _httpExceptionsLiveData.postValue(exception)
        }
    }

    private fun observeProgressBarState() {
        viewModelScope.launch {
            progressInteractor.progressStateFlow.collect { isShowing ->
                _progressBarStateLiveData.postValue(isShowing)
            }
        }
    }

    companion object {

        const val LOCATION_LISTENING_TIMEOUT = 5000L
    }
}
