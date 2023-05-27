package com.app.open_weather_map.extnesions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

typealias ViewBindingInflateProvider<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
typealias ActivityViewBindingInflateProvider<T> = (LayoutInflater) -> T

@Suppress("unused")
inline fun <reified T, S> ViewGroup.inflateWith(
    binding: T
) where T : ViewBindingInflateProvider<S>, S : ViewBinding =
    binding.invoke(LayoutInflater.from(context), this, false)
