package com.app.open_weather_map.di

import android.content.Context
import com.app.open_weather_map.di.modules.app.AppModule
import com.app.open_weather_map.di.modules.mvvm.ViewModelModule
import com.app.open_weather_map.di.modules.network.NetworkModule
import com.app.open_weather_map.di.modules.repository.RepositoryModule
import com.app.open_weather_map.di.modules.room.DatabaseModule
import com.app.open_weather_map.presentation.city.favorite.FavoriteCitiesListFragment
import com.app.open_weather_map.presentation.main.MainActivity
import com.app.open_weather_map.presentation.city.search.SearchCityFragment
import com.app.open_weather_map.presentation.city.weather.WeatherFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: WeatherFragment)
    fun inject(fragment: SearchCityFragment)
    fun inject(fragment: FavoriteCitiesListFragment)

    @Component.Builder
    interface Builder {

        fun context(@BindsInstance appContext: Context): Builder

        fun build(): AppComponent
    }
}
