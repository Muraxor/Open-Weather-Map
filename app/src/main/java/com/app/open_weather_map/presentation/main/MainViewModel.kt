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
    // FIXME: double inject
    private val progressInteractor: ProgressBarInteractor,
    private val locationObserver: LocationObserver,
    private val selectCityMediator: SelectCityMediator
) : BaseViewModel() {

    private val _httpExceptionsLiveData = MutableLiveData<NetworkException>()
    val httpExceptionsLiveData: LiveData<NetworkException> = _httpExceptionsLiveData

    private val _progressBarStateLiveData = MutableLiveData<IsShowing>()
    val progressBarStateLiveData: LiveData<IsShowing> = _progressBarStateLiveData

    init {
        observeHttpErrors()
        observeProgressBarState()
    }

    internal fun getLocation() {
        viewModelScopeWithHandler.launch {
            if (locationObserver.isLocationEnabled()) {
                wrapWithProgressBar {
                    val location = try {
                        withTimeout(5000L) {
                            locationObserver.locationFlow().first()
                        }
                    } catch (e: TimeoutCancellationException) {
                        locationObserver.lastKnownLocation()
                    }

                    selectCityMediator.loadPrimaryCity(location)
                }
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
}
