package com.app.open_weather_map.presentation.city.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import com.app.open_weather_map.base.viewmodel.BaseViewModel
import com.app.open_weather_map.domain.repositories.CityRepository
import com.app.open_weather_map.presentation.city.favorite.adapter.model.FavoriteCityUiModel
import com.app.open_weather_map.utils.CityName
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteCitiesListViewModel @Inject constructor(
    private val cityRepository: CityRepository
) : BaseViewModel() {

    private val _noFavorites = MutableLiveData<Boolean>()
    val noFavorites: LiveData<Boolean> = _noFavorites

    val favoritesListLiveData = cityRepository.getFavoritesLivaData().map {
        it.map { entity ->
            FavoriteCityUiModel(id = entity.id, name = entity.name)
        }
    }

    init {
        viewModelScopeWithHandler.launch {
            favoritesListLiveData.asFlow().collect { collection ->
                val isVisible = collection.isEmpty()
                _noFavorites.postValue(isVisible)
            }
        }
    }

    internal fun deleteFromFavorites(cityName: CityName) {
        viewModelScopeWithHandler.launch {
            getResult { cityRepository.unMarkFavorite(cityName) }
        }
    }
}
