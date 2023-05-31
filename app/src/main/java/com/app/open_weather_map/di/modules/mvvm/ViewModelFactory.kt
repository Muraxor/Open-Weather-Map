package com.app.open_weather_map.di.modules.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class DaggerViewModelFactory
@Inject constructor(
    private val viewModelsMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = viewModelsMap[modelClass]
            ?.get()
            ?.takeIf { modelClass.isAssignableFrom(it::class.java) }
            .requireNonNull { "ViewModel was not declared." }
        return viewModel as T
    }

    private fun <T> T?.requireNonNull(lazyMessage: () -> String): T = this ?: throw RuntimeException(lazyMessage())
}
