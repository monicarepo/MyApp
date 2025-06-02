package com.ms.ecommerceapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Home: BottomNavItem("home", Icons.Default.Home,"Home")
    data object Cart: BottomNavItem("cart", Icons.Default.ShoppingCart,"Cart")
    data object Account: BottomNavItem("account", Icons.Default.AccountCircle,"Account")
}