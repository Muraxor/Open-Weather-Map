package com.app.open_weather_map.di

import android.content.Context
import com.app.open_weather_map.di.modules.app.AppModule
import com.app.open_weather_map.di.modules.mvvm.ViewModelModule
import com.app.open_weather_map.di.modules.network.NetworkModule
import com.app.open_weather_map.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {

        fun context(@BindsInstance appContext: Context): Builder

        fun build(): AppComponent
    }
}
