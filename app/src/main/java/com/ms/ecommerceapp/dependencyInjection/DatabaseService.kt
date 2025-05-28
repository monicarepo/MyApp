package com.ms.ecommerceapp.dependencyInjection

import android.util.Log
import com.ms.ecommerceapp.APP_TAG
import javax.inject.Inject

class DatabaseService @Inject constructor() {
    fun log(message: String) {
        Log.d(APP_TAG, "Database Service: $message")
    }
}