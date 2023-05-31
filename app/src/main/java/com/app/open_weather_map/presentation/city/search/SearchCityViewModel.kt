package com.app.open_weather_map.presentation.city.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.open_weather_map.base.viewmodel.BaseViewModel
import com.app.open_weather_map.domain.repositories.CityRepository
import com.app.open_weather_map.extnesions.onAny
import com.app.open_weather_map.extnesions.onError
import com.app.open_weather_map.presentation.city.search.adapter.model.SuggestionUiModel
import com.app.open_weather_map.presentation.progressloader.interactor.ProgressBarInteractor
import com.app.open_weather_map.utils.SelectCityMediator
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchCityViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    private val selectCityMediator: SelectCityMediator,
    private val progressBarInteractor: ProgressBarInteractor
) : BaseViewModel(progressBarInteractor) {

    private val _searchCompleteLiveData = MutableLiveData<String>()
    val searchCompleteLiveData: LiveData<String> = _searchCompleteLiveData

    private val query = MutableSharedFlow<String>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun citiesSuggestionsFlow(): Flow<List<SuggestionUiModel>> =
        query
            .debounce(INPUT_QUERY_DEBOUNCE_MS)
            .distinctUntilChanged()
            .onEach {
                progressBarInteractor.send(true)
            }
            .flatMapLatest { name ->
                cityRepository.searchCitiesByName(name).map {
                    it.map { entity ->
                        SuggestionUiModel(entity.name, entity.isFavorite)
                    }
                }.also {
                    progressBarInteractor.send(false)
                }
            }
            .catch {
                Log.d(tag, it.stackTraceToString())
            }
            .flowOn(Dispatchers.IO)


    fun setNameQuery(name: String) {
        this.query.tryEmit(name)
    }

    fun completeSearch(query: String) {
        viewModelScope.launch {
            getResult {
                selectCityMediator.pushCityName(query)
            }.onAny {
                _searchCompleteLiveData.value = query
            }.onError {
                // TODO: show error
            }
        }
    }

    internal fun oppositeFavoriteStatus(model: SuggestionUiModel) {
        viewModelScopeWithHandler.launch {
            getResult {
                val cityName = model.name
                if (model.isFavorite) {
                    cityRepository.unMarkFavorite(cityName)
                } else {
                    cityRepository.markFavorite(cityName)
                }
            }
        }
    }

    companion object {

        const val INPUT_QUERY_DEBOUNCE_MS = 300L
    }
}
