package com.ms.ecommerceapp.screens.accounts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ms.apimockdata.model.AddressModel


@Composable
fun SavedAddressScreen(
    addresses: List<AddressModel>,
    navController: NavHostController,
    onEditClick: (AddressModel) -> Unit = {},
    onDeleteClick: (AddressModel) -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Saved Addresses", style = MaterialTheme.typography.headlineSmall)
        }

        if (addresses.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No saved addresses.")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(addresses) { address ->
                    AddressCard(address, onEditClick, onDeleteClick)
                }
            }
        }
    }
}

@Composable
fun AddressCard(
    address: AddressModel,
    onEditClick: (AddressModel) -> Unit,
    onDeleteClick: (AddressModel) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(address.name, style = MaterialTheme.typography.titleMedium)
            Text(address.phone, style = MaterialTheme.typography.bodySmall)
            Text("${address.addressLine}, ${address.city}, ${address.zip}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { onEditClick(address) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { onDeleteClick(address) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedAddressPreview() {
    val dummyAddresses = listOf(
        AddressModel(1, "Monica", "1234567890", "8256 Westerville", "Ohio", "43081"),
        AddressModel(2, "Mishal", "9876543210", "456 5th Ave", "New York", "10002")
    )
    SavedAddressScreen(navController = rememberNavController(), addresses = dummyAddresses)
}
