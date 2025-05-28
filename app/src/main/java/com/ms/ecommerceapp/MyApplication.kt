package com.ms.ecommerceapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

val APP_TAG = "MyApplication"

@HiltAndroidApp
class MyApplication: Application() {

}