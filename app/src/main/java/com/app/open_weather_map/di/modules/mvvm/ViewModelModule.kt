package com.app.open_weather_map.di.modules.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.open_weather_map.presentation.city.favorite.FavoriteCitiesListViewModel
import com.app.open_weather_map.presentation.main.MainViewModel
import com.app.open_weather_map.presentation.city.search.SearchCityViewModel
import com.app.open_weather_map.presentation.city.weather.CitySharedViewModel
import com.app.open_weather_map.presentation.city.weather.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindDaggerViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @[Binds IntoMap ViewModelKey(MainViewModel::class)]
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(WeatherViewModel::class)]
    fun bindWeatherViewModel(viewModel: WeatherViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(FavoriteCitiesListViewModel::class)]
    fun bindFavoriteCitiesListViewModel(viewModel: FavoriteCitiesListViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(SearchCityViewModel::class)]
    fun bindSearchCityViewModel(viewModel: SearchCityViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(CitySharedViewModel::class)]
    fun bindCitySharedViewModel(viewModel: CitySharedViewModel): ViewModel
}
