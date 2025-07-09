package com.ms.location

import android.location.Location

interface LocationCallback {
    fun onLocationResult(location: Location?)
}