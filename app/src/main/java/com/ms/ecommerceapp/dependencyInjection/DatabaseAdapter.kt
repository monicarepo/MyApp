package com.ms.ecommerceapp.dependencyInjection


import android.content.Context
import com.ms.ecommerceapp.APP_TAG
import com.ms.ecommerceapp.util.Log
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/***
 * Dependency Injection in Hilt
 * Start with the @HiltAndroidApp annotation
 * after the application annotation start with @AndroidEntryPoint annotation in Activities and Fragments
 * Types of Injections
 *      1.Constructor Injection
 *          While using the constructor injection for dependency injection, hilt will take care of the initialization(instantiation)
 *          for example : here I attached the databaseService to the DatabaseAdapter
 *      2.Filed Injection
 *          I created the lateinit var for the databaseAdapter in the SignInActivity. but i'm not initializing it
 *          but the hilt will take care of it
 *      3.Method Injection
 *          I created the directToDatabase method in the SignInActivity using the method injection.
 *          I'm not called this function anywhere in the code but hilt will take care of it
 *          it's executed first when the app is launched. it's executed the function First
 *          Message Printing order is :
 *              Database Service: Hello Hilt Method Injection Service
 *              Database Adapter: Hello Hilt
 *              Database Service: Hello Hilt
 *  Modules
 *      Interface injections with @Binds
 *      Instance injections with @Provides
 *      Same types of Multiple bindings
 *      Providing context
 */

class DatabaseAdapter @Inject constructor(@ActivityContext private val context: Context, private val databaseService: DatabaseService) {
    fun log(message: String) {
//        Log.d(APP_TAG, "Database Adapter: $message")
        Log.d(APP_TAG, "Database Adapter: $message")
        databaseService.log(message)
        Log.d(APP_TAG, "Context available: $context")
    }
}