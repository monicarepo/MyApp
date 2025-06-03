package com.ms.ecommerceapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ms.apimockdata.Utility
import com.ms.apimockdata.model.AddressModel
import com.ms.apimockdata.model.CartModel
import com.ms.apimockdata.model.NotificationItem
import com.ms.apimockdata.model.OrderDetailModel
import com.ms.apimockdata.model.OrderModel
import com.ms.apimockdata.model.ProductListModel
import com.ms.apimockdata.model.UserListModel
import com.ms.apptheme.route.Route
import com.ms.ecommerceapp.navigation.BottomNavItem
import com.ms.ecommerceapp.navigation.BottomNavigationBar
import com.ms.ecommerceapp.screens.accounts.MyOrdersScreen
import com.ms.ecommerceapp.screens.accounts.SavedAddressScreen
import com.ms.ecommerceapp.screens.accounts.SettingsScreen
import com.ms.ecommerceapp.screens.accounts.WishlistScreen
import com.ms.ecommerceapp.screens.settings.AboutAppScreen
import com.ms.ecommerceapp.screens.settings.FAQScreen
import com.ms.ecommerceapp.screens.settings.HelpSupportScreen
import com.ms.ecommerceapp.screens.settings.NotificationScreen
import com.ms.ecommerceapp.screens.settings.PrivacyPolicyScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val apiMockUtility = remember { Utility() }

    var cartItems by remember { mutableStateOf(listOf<CartModel>()) }
    var productList by remember { mutableStateOf(listOf<ProductListModel>()) }
    var usersList by remember { mutableStateOf(listOf<UserListModel>()) }
    var orderDetails by remember { mutableStateOf(listOf<OrderDetailModel>()) }
    var orderDetailByUser by remember { mutableStateOf(listOf<CartModel>()) }
    var newOrder by remember { mutableStateOf(OrderModel()) }
    var addressList by remember { mutableStateOf(listOf<AddressModel>()) }
    var notificationList by remember { mutableStateOf(listOf<NotificationItem>()) }

    LaunchedEffect(Unit) {
        cartItems = apiMockUtility.getCartItems(context)
        productList = apiMockUtility.getProductsList(context)
        usersList = apiMockUtility.getUsersList(context)
        orderDetails = apiMockUtility.getOrderDetails(context)
        orderDetailByUser = apiMockUtility.getOrderPlacedByUser(context)
        newOrder = apiMockUtility.newOrderOrUpdateOder(context)
        addressList = apiMockUtility.getAddressDetails(context)
        notificationList = apiMockUtility.getNotificationList(context)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                if (cartItems.isNotEmpty()) {
                    HomeScreen(productList, cartItems[0],navController)
                }
            }
//            composable(BottomNavItem.Home.route) { ImageLoaderScreen() }
            composable(BottomNavItem.Cart.route) {
                CartScreen(cartItems[0], navController)
            }
            composable(BottomNavItem.Account.route) {
                AccountScreen(navController)
            }
            composable(Route.Dashboard.ORDER_PLACED) {
                OrderPlacedScreen(navController)
            }
            composable(Route.Dashboard.MY_ORDERS) {
                MyOrdersScreen(orderDetails,navController)
            }
            composable(Route.Dashboard.WISHLIST) {
                WishlistScreen(productList, navController)
            }
            composable(Route.Dashboard.SAVED_ADDRESS) {
                SavedAddressScreen(addressList,navController)
            }
            composable(Route.Dashboard.SETTINGS) {
                SettingsScreen(navController)
            }
            composable(Route.Dashboard.NOTIFICATION) {
                NotificationScreen(notificationList, navController)
            }
            composable(Route.Dashboard.PRIVACY) {
                PrivacyPolicyScreen(navController)
            }
            composable(Route.Dashboard.ABOUT) {
                AboutAppScreen(navController)
            }
            composable(Route.Dashboard.SUPPORT) {
                HelpSupportScreen(navController)
            }
            composable(Route.Dashboard.FAQ) {
                FAQScreen(navController)
            }
        }
    }
}