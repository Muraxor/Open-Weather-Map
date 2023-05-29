package com.app.open_weather_map.presentation.city.weather

import androidx.lifecycle.*
import com.app.open_weather_map.base.viewmodel.BaseViewModel
import com.app.open_weather_map.domain.repositories.CityRepository
import com.app.open_weather_map.extnesions.valueOrThrow
import com.app.open_weather_map.utils.SelectCityMediator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    selectCityMediator: SelectCityMediator
) : BaseViewModel() {

    private var cityName = MutableLiveData<String>()

    val isFavorite = cityName.switchMap { name ->
        cityRepository.isFavoriteLiveData(name)
    }

    init {
        selectCityMediator.city
            .onEach { name -> cityName.postValue(name) }
            .launchIn(viewModelScopeWithHandler)
    }

    internal fun oppositeFavoriteStatus() {
        viewModelScopeWithHandler.launch {
            val previous = isFavorite.valueOrThrow()
            getResult {
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
