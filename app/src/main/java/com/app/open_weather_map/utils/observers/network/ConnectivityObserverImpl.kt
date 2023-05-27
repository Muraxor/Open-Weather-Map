package com.app.open_weather_map.utils.observers.network

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.RemoteException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConnectivityObserverImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : ConnectivityObserver {

    @SuppressLint("ObsoleteSdkInt")
    private fun registerCallback(networkCallback: ConnectivityManager.NetworkCallback) =
        with(connectivityManager) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                registerDefaultNetworkCallback(networkCallback)
            } else {
                val requestBuilder = NetworkRequest.Builder()
                registerNetworkCallback(requestBuilder.build(), networkCallback)
            }
        }

    override fun networkStatusFlow(): Flow<ConnectivityObserver.Status> = callbackFlow {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch { send(ConnectivityObserver.Status.AVAILABLE) }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                launch { send(ConnectivityObserver.Status.LOSING) }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                launch { send(ConnectivityObserver.Status.LOST) }
            }

            override fun onUnavailable() {
                super.onUnavailable()
                launch { send(ConnectivityObserver.Status.UNAVAILABLE) }
            }
        }.also { callback ->
            registerCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
    }.distinctUntilChanged()

    override fun getStatus(): ConnectivityObserver.Status = try {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).let { connected ->
                if (connected == true) {
                    ConnectivityObserver.Status.AVAILABLE
                } else {
                    ConnectivityObserver.Status.UNAVAILABLE
                }
            }
    } catch (e: RemoteException) {
        ConnectivityObserver.Status.UNAVAILABLE
    }
}
