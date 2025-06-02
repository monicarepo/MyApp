package com.ms.ecommerceapp

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

val APP_TAG = "MyApplication"

@HiltAndroidApp
class MyApplication : Application() {
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
    }
}
