package com.fetch.rewards.exercise.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

sealed class ConnectivityStatus{
    object Connected : ConnectivityStatus()
    object DisConnected : ConnectivityStatus()
    object Unknown : ConnectivityStatus()
}

class ConnectivityService @Inject constructor(val context : Context) {

    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager

    private val _isConnected = MutableStateFlow<Boolean>(false)
    val isConnected : StateFlow<Boolean> = _isConnected.asStateFlow()

    val connectionStatus : Flow<ConnectivityStatus> = callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                Log.d("RES_NETX","Connected")
                _isConnected.value = true
               trySend(ConnectivityStatus.Connected)
            }

            // lost network connection
            override fun onLost(network: Network) {
                Log.d("RES_NETX","DisConnected")
                _isConnected.value = false
                trySend(ConnectivityStatus.DisConnected)
            }
        }

            connectivityManager.registerDefaultNetworkCallback(networkCallback)

        awaitClose{
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)


}

