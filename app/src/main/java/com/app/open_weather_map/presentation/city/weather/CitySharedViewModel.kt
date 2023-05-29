package com.app.open_weather_map.presentation.city.weather

import android.content.Context
import androidx.lifecycle.*
import com.app.open_weather_map.base.viewmodel.BaseViewModel
import com.app.open_weather_map.domain.repositories.CityWeatherRepository
import com.app.open_weather_map.presentation.city.weather.adapter.model.CityWeatherDetailsUiModel
import com.app.open_weather_map.utils.SelectCityMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitySharedViewModel @Inject constructor(
    private val appContext: Context,
    private val cityWeatherRepository: CityWeatherRepository,
    private val selectCityMediator: SelectCityMediator
) : BaseViewModel() {

    val city = selectCityMediator.city.asLiveData()

    private val _weatherDetails = MutableLiveData<List<CityWeatherDetailsUiModel>>()
    val weatherDetails: LiveData<List<CityWeatherDetailsUiModel>> = _weatherDetails

    @OptIn(ExperimentalCoroutinesApi::class)
    val cityWeatherFlow = city.asFlow().flatMapLatest {
        cityWeatherRepository.getCityAndWeatherFlow(it)
    }

    init {
        observeWeatherDetails()
    }

    private fun observeWeatherDetails() {
        viewModelScope.launch {
            cityWeatherFlow
                .collect {
                    if (it == null) {
                        _weatherDetails.postValue(emptyList())
                    } else {
                        val list = CityWeatherDetailsUiModel.setupList(
                            it.city.name,
                            it.weather,
                            appContext
                        )
                        _weatherDetails.postValue(list)
                    }
                }
        }
    }

    fun onCityChosen(cityName: String) {
        viewModelScopeWithHandler.launch {
            wrapWithProgressBar {
                selectCityMediator.pushCityName(cityName)
            }
        }
    }
}
