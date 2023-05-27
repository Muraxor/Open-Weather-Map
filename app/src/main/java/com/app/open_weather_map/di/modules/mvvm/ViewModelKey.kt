package com.app.open_weather_map.di.modules.mvvm

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@[MapKey MustBeDocumented
Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_SETTER)
Retention(AnnotationRetention.RUNTIME)]
annotation class ViewModelKey(val value: KClass<out ViewModel>)
