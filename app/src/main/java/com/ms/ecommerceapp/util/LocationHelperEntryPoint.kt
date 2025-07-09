package com.ms.ecommerceapp.util

import com.ms.location.LocationHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LocationHelperEntryPoint {
    fun locationHelper(): LocationHelper
}