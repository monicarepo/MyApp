package com.ms.ecommerceapp.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ms.apptheme.ui.theme.AppTheme
import com.ms.networklibrary.WifiHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NetworkStateActivity: AppCompatActivity() {

    @Inject
    lateinit var wifiHelper: WifiHelper
    private val locationPermissionRequestCode = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("NetworkActivityCalled")
//        wifiHelper = WifiHelper(this)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        checkAndRequestLocationPermission()
    }

    private fun getSSID() {
        println("getSSID Called")
        if (wifiHelper.hasLocationPermission() && wifiHelper.isLocationEnabled()) {
            val ssid = wifiHelper.getConnectedWifiSSID()
            println("Connected to: $ssid")
        } else {
            println("Permission or location disabled")
        }
    }

    private fun checkAndRequestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionRequestCode
            )
        } else {
            getSSID()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionRequestCode &&
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            getSSID()
        } else {
            println("❌ Location permission denied")
        }
    }
}
