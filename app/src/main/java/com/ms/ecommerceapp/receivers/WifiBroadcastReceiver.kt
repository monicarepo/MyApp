package com.ms.ecommerceapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.util.Log

class WifiBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            WifiManager.WIFI_STATE_CHANGED_ACTION -> {
                val state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
                when(state) {
                    WifiManager.WIFI_STATE_ENABLED -> {
                        // Wifi is enabled
                        Log.d("WIFI_CHECK", "Wi-Fi Enabled")
                    }
                    WifiManager.WIFI_STATE_DISABLED -> {
                        // Wifi is disabled
                        Log.d("WIFI_CHECK", "Wi-Fi Disabled")
                    }
                }
            }
            WifiManager.NETWORK_STATE_CHANGED_ACTION -> {
                if (context != null) {
                    val network = getCurrentNetworkType(context)
                    Log.d("WIFI_CHECK", "ConnectionStatus $network")
                }
            }

        }
    }

    fun getCurrentNetworkType(context: Context): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return "No Connection"
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return "No Connection"

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)  -> "Wi-Fi"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Mobile Data"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
            else -> "Other"
        }
    }

}