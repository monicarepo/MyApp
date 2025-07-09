package com.ms.ecommerceapp

import android.app.Application
import android.location.Location
import dagger.hilt.android.HiltAndroidApp
import com.ms.ecommerceapp.util.LocationHelperEntryPoint
import com.ms.location.LocationCallback
import com.ms.location.LocationHelper
import com.ms.networklibrary.WifiHelper
import dagger.hilt.android.EntryPointAccessors
import jakarta.inject.Inject

val APP_TAG = "MyApplication"

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var locationProvider: LocationHelper

    @Inject
    lateinit var wifiHelper: WifiHelper

//    override fun newImageLoader(context: PlatformContext): ImageLoader {
//        val logging = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//        val retryInterceptor = Interceptor { chain ->
//            var response = chain.proceed(chain.request())
//            var tryCount = 0
//            while (!response.isSuccessful && tryCount < 3) {
//                tryCount++
//                response.close()
//                response = chain.proceed(chain.request())
//            }
//            response
//        }
//
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(retryInterceptor)
//            .addInterceptor(logging)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .build()
//
//        return ImageLoader.Builder(context)
//            .components {
//                add(OkHttpNetworkFetcherFactory(callFactory = { okHttpClient }))
//            }
//            .build()
//    }

    override fun onCreate() {
        super.onCreate()
//        SingletonImageLoader.setSafe(this)
        locationProvider = EntryPointAccessors.fromApplication(
            this,
            LocationHelperEntryPoint::class.java
        ).locationHelper()

        wifiHelper = WifiHelper(this)
    }


    fun getLocation() {
        locationProvider.getCurrentLocation(object : LocationCallback {
            override fun onLocationResult(location: Location?) {
                println("DeviceLocation : $location")
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    println("Lat: $latitude, Lng: $longitude")
                } else {
                    println("Location not available")
                }
            }
        })
    }
}
