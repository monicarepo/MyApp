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
        const val HOME = "home"
        const val CART = "cart"
        const val ACCOUNT = "account"
        const val MY_ORDERS = "my_orders"
        const val WISHLIST = "wish_list"
        const val SAVED_ADDRESS = "saved_address"
        const val SETTINGS = "settings"
        const val NOTIFICATION = "notification"
        const val PRIVACY = "privacy"
        const val ABOUT = "about"
        const val SUPPORT = "support"
        const val FAQ = "faq"
        const val PRODUCT_DETAIL = "product_detail"
        fun productDetail(productId: Int) = "$PRODUCT_DETAIL/$productId"

    }
}