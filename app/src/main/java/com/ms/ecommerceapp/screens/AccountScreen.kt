package com.ms.ecommerceapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ms.apimockdata.model.AccountOptionItem
import com.ms.apptheme.route.Route

@Composable
fun AccountScreen(navController: NavHostController) {
    val accountOptions = listOf(
        AccountOptionItem(Icons.Default.Info, "My Orders", Route.Dashboard.MY_ORDERS),
        AccountOptionItem(Icons.Default.Favorite, "Wishlist", Route.Dashboard.WISHLIST),
        AccountOptionItem(Icons.Default.LocationOn, "Saved Addresses", Route.Dashboard.SAVED_ADDRESS),
        AccountOptionItem(Icons.Default.Settings, "Settings", Route.Dashboard.SETTINGS)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        ProfileHeader()

        Spacer(modifier = Modifier.height(24.dp))
        accountOptions.forEach { item ->
            AccountOption(icon = item.icon, title = item.title) {
                navController.navigate(item.route)
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        LogoutButton()
    }
}

@Composable
fun ProfileHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile",
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Column {
            Text(text = "Monica", style = MaterialTheme.typography.titleMedium)
            Text(text = "monica@gmail.com", style = MaterialTheme.typography.bodySmall)
        }
    }
}


@Composable
fun AccountOption(icon: ImageVector, title: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
fun LogoutButton(onLogout: () -> Unit = {}) {
    Button(
        onClick = onLogout,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        )
    ) {
        Text("Logout", color = MaterialTheme.colorScheme.onError)
    }
}
