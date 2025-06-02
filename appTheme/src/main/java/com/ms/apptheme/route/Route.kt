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

    object Dashboard {
        const val ORDER_PLACED = "order_placed"
        const val Home = "home"
        const val Cart = "cart"
        const val Account = "account"
    }
}