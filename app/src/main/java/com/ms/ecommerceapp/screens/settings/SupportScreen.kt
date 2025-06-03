package com.ms.ecommerceapp.screens.settings

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.ms.apptheme.route.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(navController: NavHostController) {
    val context = LocalContext.current
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
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HorizontalDivider()

            // Contact via Email
            SupportOption(
                icon = Icons.Default.Email,
                title = "Email Us",
                description = "support@ecommerceapp.com"
            ) {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:support@ecommerceapp.com".toUri()
                }
                context.startActivity(intent)
            }

            // Contact via Phone
            SupportOption(
                icon = Icons.Default.Call,
                title = "Call Us",
                description = "+1 234 567 890"
            ) {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = "tel:+1234567890".toUri()
                }
                context.startActivity(intent)
            }

            // FAQs
            SupportOption(
                icon = Icons.Default.Info,
                title = "FAQs",
                description = "Frequently Asked Questions"
            ) {
                navController.navigate(Route.Dashboard.FAQ)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "Need more help? Contact us 24/7 and we'll respond as soon as possible.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun SupportOption(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
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
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(text = description, style = MaterialTheme.typography.bodySmall)
        }
    }
}
