package com.app.open_weather_map.extnesions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.app.open_weather_map.BuildConfig

fun Fragment.navigate(
    @IdRes actionID: Int,
    options: NavOptions? = null,
    bundle: Bundle? = null
) {
    findNavController().navigate(actionID, bundle, options)
}

fun Fragment.navigate(
    actionID: NavDirections
) = findNavController().navigate(actionID)

fun Fragment.debugNavigate(
    @IdRes actionID: Int
) {
    if (BuildConfig.DEBUG) {
        navigate(actionID)
    }
}

fun Fragment.popBackStack() {
    findNavController().popBackStack()
}
