package com.ms.ecommerceapp.screens.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ms.apimockdata.model.AccountOptionItem
import com.ms.apptheme.route.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    val settingOptions = listOf(
        AccountOptionItem(Icons.Default.Notifications, "Notifications", Route.Dashboard.NOTIFICATION),
        AccountOptionItem(Icons.Default.Lock, "Privacy Policy", Route.Dashboard.PRIVACY),
        AccountOptionItem(Icons.Default.Info, "About App", Route.Dashboard.ABOUT),
        AccountOptionItem(Icons.Default.MailOutline, "Help & Support", Route.Dashboard.SUPPORT)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            settingOptions.forEach { item ->
                SettingOption(icon = item.icon, title = item.title) {
                    navController.navigate(item.route)
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "App Version 1.0.0",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun SettingOption(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
    }
}
