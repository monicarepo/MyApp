package com.ms.ecommerceapp.util

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import com.ms.location.LocationCallback
import com.ms.location.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(private val locationProvider: LocationHelper): ViewModel() {
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun fetchLocation() {
        locationProvider.getCurrentLocation(object : LocationCallback {
            override fun onLocationResult(location: Location?) {
                println("Location : $location")
            }
        })
    }
}
