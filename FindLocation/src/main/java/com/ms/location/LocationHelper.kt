package com.ms.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class LocationHelper @Inject constructor(@ApplicationContext private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getCurrentLocation(callback: LocationCallback) {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            callback.onLocationResult(null)
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                println("getCurrentLocation : $location")
                callback.onLocationResult(location)
            }
            .addOnFailureListener { error ->
                println("getCurrentLocation failure: $error")
                callback.onLocationResult(null)
            }
    }
}