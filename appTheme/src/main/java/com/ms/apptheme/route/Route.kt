package com.ms.apptheme.route

/** Class contains all route information  */
object Route {

    object Welcome {
        const val SPLASH = "splash"
        const val TERMS = "terms"
    }

    object Authentification {
        const val REGISTER = "register"

        object login {
            const val LOGIN = "login"
            const val FORGOT_PASSWORD = "forgot-password"
        }
    }
}