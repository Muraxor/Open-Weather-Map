package com.app.open_weather_map.utils.observers.location

import android.Manifest.permission
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.location.LocationListenerCompat
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocationObserverImpl @Inject constructor(
    private val locationManager: LocationManager
) : LocationObserver {

    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    override fun locationFlow(): Flow<Location> = callbackFlow {
        LocationListenerCompat { location ->
            launch { send(location) }
        }.also { listener ->
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_MS,
                MIN_DISTANCE_M,
                listener
            )
            awaitClose {
                locationManager.removeUpdates(listener)
            }
        }
    }
        .cancellable()
        .distinctUntilChanged()

    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    override fun lastKnownLocation(): Location? {
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            ?: locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
    }

    override fun isLocationEnabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            locationManager.isLocationEnabled
        } else {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
    }

    companion object {

        private const val MIN_TIME_MS = 1000L
        private const val MIN_DISTANCE_M = 1F
    }
}
