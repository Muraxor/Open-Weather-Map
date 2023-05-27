package com.app.open_weather_map.di.modules

import com.app.open_weather_map.data.network.interceptors.HttpErrorsInterceptor
import com.app.open_weather_map.data.network.interceptors.connection.InternetConnectionInterceptor
import com.app.open_weather_map.utils.client.NetworkMessageResolver
import com.app.open_weather_map.utils.observers.httperrors.HttpErrorManager
import com.app.open_weather_map.utils.observers.network.ConnectivityObserver
import dagger.Module
import dagger.Provides

@Module
class InterceptorsModule {

    @Provides
    fun provideHttpErrorsInterceptor(
        httpErrorManager: HttpErrorManager,
        networkMessageResolver: NetworkMessageResolver
    ): HttpErrorsInterceptor = HttpErrorsInterceptor(httpErrorManager, networkMessageResolver)

    @Provides
    fun provideInternetConnectionInterceptor(
        httpErrorManager: HttpErrorManager,
        networkMessageResolver: NetworkMessageResolver,
        connectivityObserver: ConnectivityObserver
    ): InternetConnectionInterceptor = InternetConnectionInterceptor(
        httpErrorManager,
        networkMessageResolver,
        connectivityObserver
    )
}
