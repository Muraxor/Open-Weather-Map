package com.app.open_weather_map.presentation.city.favorite

import android.util.Log
import androidx.lifecycle.*
import com.app.open_weather_map.base.viewmodel.BaseViewModel
import com.app.open_weather_map.domain.repositories.CityRepository
import com.app.open_weather_map.extnesions.onAny
import com.app.open_weather_map.presentation.city.favorite.adapter.model.FavoriteCityUiModel
import com.app.open_weather_map.presentation.progressloader.interactor.ProgressBarInteractor
import com.app.open_weather_map.utils.CityName
import com.app.open_weather_map.utils.SelectCityMediator
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteCitiesListViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    private val selectCityMediator: SelectCityMediator,
    progressBarInteractor: ProgressBarInteractor
) : BaseViewModel(progressBarInteractor) {

    private val _cityClickedLiveData = MutableLiveData<String>()
    val cityClickedLiveData: LiveData<String> = _cityClickedLiveData

    private val _noFavorites = MutableLiveData<Boolean>()
    val noFavorites: LiveData<Boolean> = _noFavorites

    val favoritesListLiveData = cityRepository.getFavoritesLivaData().map {
        it.map { entity ->
            FavoriteCityUiModel(id = entity.id, name = entity.name)
        }
    }

    init {
        viewModelScopeWithHandler.launch {
            favoritesListLiveData
                .asFlow()
                .onEach {
                    progressBarInteractor.send(true)
                }
                .catch { Log.d(tag, it.stackTraceToString()) }
                .collect { collection ->
                    val isVisible = collection.isEmpty()
                    _noFavorites.postValue(isVisible)
                    progressBarInteractor.send(false)
                }
        }
    }

    internal fun deleteFromFavorites(cityName: CityName) {
        viewModelScopeWithHandler.launch {
            getResult { cityRepository.unMarkFavorite(cityName) }
        }
    }

    internal fun onCityClicked(cityName: CityName) {
        viewModelScope.launch {
            getResult {
                selectCityMediator.pushCityName(cityName)
            }.onAny {
                _cityClickedLiveData.value = cityName
            }
        }
    }
}
