package com.app.open_weather_map.presentation.city.weather

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.app.open_weather_map.base.viewmodel.BaseViewModel
import com.app.open_weather_map.domain.repositories.CityRepository
import com.app.open_weather_map.domain.repositories.CityWeatherRepository
import com.app.open_weather_map.extnesions.valueOrThrow
import com.app.open_weather_map.presentation.city.weather.adapter.model.CityWeatherDetailsUiModel
import com.app.open_weather_map.presentation.progressloader.interactor.ProgressBarInteractor
import com.app.open_weather_map.utils.SelectCityMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
class WeatherViewModel @Inject constructor(
    private val context: Context,
    private val cityRepository: CityRepository,
    private val cityWeatherRepository: CityWeatherRepository,
    progressBarInteractor: ProgressBarInteractor,
    selectCityMediator: SelectCityMediator
) : BaseViewModel(progressBarInteractor) {

    private var cityName = MutableLiveData<String>()

    val isFavorite = cityName.switchMap { name ->
        cityRepository.isFavoriteLiveData(name)
    }

    private val _weatherDetails = MutableLiveData<List<CityWeatherDetailsUiModel>>()
    val weatherDetails: LiveData<List<CityWeatherDetailsUiModel>> = _weatherDetails

    private val cityWeatherFlow = cityName.switchMap {
        cityWeatherRepository.getCityAndWeatherLiveData(it)
    }
        .asFlow()
        .catch { Log.d(tag, it.stackTraceToString()) }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()

    init {
        selectCityMediator.city
            .onEach { name -> cityName.postValue(name) }
            .launchIn(viewModelScopeWithHandler)
        observeWeatherDetails()
    }

    private fun observeWeatherDetails() {
        cityWeatherFlow
            .onEach {
                if (it == null) {
                    _weatherDetails.postValue(emptyList())
                } else {
                    CityWeatherDetailsUiModel.setupList(
                        it.city.name,
                        it.weather,
                        context
                    ).also { list ->
                        _weatherDetails.postValue(list)
                    }
                }
            }
            .catch { Log.d(tag, it.stackTraceToString()) }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScopeWithHandler)
    }

    internal fun oppositeFavoriteStatus() {
        viewModelScopeWithHandler.launch {
            getResult {
                val previous = isFavorite.valueOrThrow()
                val cityName = cityName.valueOrThrow()
                if (previous) {
                    cityRepository.unMarkFavorite(cityName)
                } else {
                    cityRepository.markFavorite(cityName)
                }
            }
        }
    }
}
