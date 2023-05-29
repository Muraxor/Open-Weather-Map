package com.app.open_weather_map.di.modules.app

import com.app.open_weather_map.di.modules.DispatcherModule
import com.app.open_weather_map.di.modules.android.SystemModule
import com.app.open_weather_map.presentation.progressloader.interactor.ProgressBarInteractor
import com.app.open_weather_map.presentation.progressloader.interactor.ProgressBarInteractorImpl
import com.app.open_weather_map.utils.SelectCityMediator
import com.app.open_weather_map.utils.SelectCityMediatorImpl
import com.app.open_weather_map.utils.client.NetworkMessageResolver
import com.app.open_weather_map.utils.client.NetworkMessageResolverImpl
import com.app.open_weather_map.utils.observers.httperrors.HttpErrorManager
import com.app.open_weather_map.utils.observers.httperrors.HttpErrorManagerImpl
import com.app.open_weather_map.utils.observers.location.LocationObserver
import com.app.open_weather_map.utils.observers.location.LocationObserverImpl
import com.app.open_weather_map.utils.observers.network.ConnectivityObserver
import com.app.open_weather_map.utils.observers.network.ConnectivityObserverImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        SystemModule::class,
        DispatcherModule::class
    ]
)
interface AppModule {

    @Binds
    @Singleton
    fun bindHttpErrorManager(impl: HttpErrorManagerImpl): HttpErrorManager

    @Binds
    @Singleton
    fun bindNetworkMessageResolver(impl: NetworkMessageResolverImpl): NetworkMessageResolver

    @Binds
    @Singleton
    fun bindConnectivityObserver(impl: ConnectivityObserverImpl): ConnectivityObserver

    @Binds
    @Singleton
    fun bindLocationObserver(impl: LocationObserverImpl): LocationObserver


    @Binds
    @Singleton
    fun bindSelectCityMediator(impl: SelectCityMediatorImpl): SelectCityMediator

    @Binds
    @Singleton
    fun bindProgressBarInteractor(impl: ProgressBarInteractorImpl): ProgressBarInteractor
}
