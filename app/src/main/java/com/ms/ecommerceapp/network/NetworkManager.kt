package com.ms.ecommerceapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log

object NetworkManager {
    private var connectivityManager: ConnectivityManager? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    fun initialize(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // Network is available
                val networkType = getNetworkType(network)
                when(networkType) {
                    NetworkType.WIFI -> {
                        Log.d("NetworkManager", "Wi-Fi Connected")
                    }

                    NetworkType.MOBILE_DATA -> {
                        Log.d("NetworkManager", "Mobile Data Connected")
                    }

                    NetworkType.ETHERNET -> {
                        Log.d("NetworkManager", "Ethernet Connected")
                    }
                    NetworkType.OTHER -> {
                        Log.d("NetworkManager", "Other Connected")
                    }
                    NetworkType.NO_CONNECTION -> {
                        Log.d("NetworkManager", "No Connection")
                    }
                }
            }

            override fun onUnavailable() {
                super.onUnavailable()
                Log.d("NetworkManager", "Network Disconnected")
            }
        }

        connectivityManager?.registerNetworkCallback(request, networkCallback!!)
    }

    fun getNetworkType(network: Network?): NetworkType {
        if (network == null) return NetworkType.NO_CONNECTION
        val capabilities = connectivityManager?.getNetworkCapabilities(network) ?: return NetworkType.NO_CONNECTION
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.MOBILE_DATA
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.ETHERNET
            else -> NetworkType.OTHER
        }
    }

    fun unRegisterNetworkCallback() {
        connectivityManager?.unregisterNetworkCallback(networkCallback!!)
    }

    fun getConnectionStatus(context: Context): Boolean {
        val network = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager?.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


}

enum class NetworkType {
    WIFI,
    MOBILE_DATA,
    ETHERNET,
    OTHER,
    NO_CONNECTION
}
