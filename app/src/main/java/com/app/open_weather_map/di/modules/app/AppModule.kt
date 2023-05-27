package com.app.open_weather_map.di.modules.app

import com.app.open_weather_map.presentation.progressloader.interactor.ProgressBarInteractor
import com.app.open_weather_map.presentation.progressloader.interactor.ProgressBarInteractorImpl
import com.app.open_weather_map.utils.client.NetworkMessageResolver
import com.app.open_weather_map.utils.client.NetworkMessageResolverImpl
import com.app.open_weather_map.utils.observers.httperrors.HttpErrorManager
import com.app.open_weather_map.utils.observers.httperrors.HttpErrorManagerImpl
import com.app.open_weather_map.utils.observers.network.ConnectivityObserver
import com.app.open_weather_map.utils.observers.network.ConnectivityObserverImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
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
    fun bindProgressBarInteractor(impl: ProgressBarInteractorImpl): ProgressBarInteractor
}
