package com.ms.ecommerceapp.navigation

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ms.apptheme.ui.theme.onPrimaryLight
import com.ms.apptheme.ui.theme.onSurfaceVariantLight
import com.ms.apptheme.ui.theme.primaryLight
import com.ms.apptheme.ui.theme.secondaryLight
import com.ms.apptheme.ui.theme.tertiaryLight

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Cart,
        BottomNavItem.Account
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.lowercase() ?:BottomNavItem.Home.route

    BottomNavigation(
        backgroundColor = onPrimaryLight
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.label)},
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                selectedContentColor = primaryLight,
                unselectedContentColor = onSurfaceVariantLight,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}