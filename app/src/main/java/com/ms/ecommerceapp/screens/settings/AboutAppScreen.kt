package com.ms.ecommerceapp.screens.settings

import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ms.apptheme.route.Route
import com.ms.ecommerceapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(navController: NavHostController) {
    val context = LocalContext.current
    val versionName = remember {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionName ?: "1.0"
        } catch (e: PackageManager.NameNotFoundException) {
            "1.0"
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("About App") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp)
                    .background(Color.Red),
            )

            // App Name
            Text("EcommerceApp", style = MaterialTheme.typography.headlineSmall)

            // App Version
            Text("Version $versionName", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(24.dp))

            // Description
            Text(
                "EcommerceApp is your one-stop solution for buying everything from electronics to fashion. Fast, secure, and easy to use!",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider()

            SettingItem(title = "Privacy Policy") {
                navController.navigate(Route.Dashboard.PRIVACY)
            }
            SettingItem(title = "Contact Us") {
                navController.navigate(Route.Dashboard.SUPPORT)
            }
            SettingItem(title = "Terms of Service") {
                navController.navigate(Route.Dashboard.PRIVACY)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer
            Text("© 2025 EcommerceApp Inc.", style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun SettingItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, style = MaterialTheme.typography.bodyMedium)
    }
}
