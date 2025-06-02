package com.ms.ecommerceapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.ms.apimockdata.model.CartModel
import com.ms.apptheme.route.Route
import com.ms.ecommerceapp.R

@Composable
fun CartScreen(cartItems: CartModel, navController: NavHostController) {
    val subtotal = calculateTotal(cartItems)
    val shipping = 5.00

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("My Cart", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(cartItems.cartItems.size) { index ->
                val item = cartItems.cartItems[index]
                item.quantity?.let { CartItem(name = "Product ${item.name}", price = "$${item.price}", quantity = it, imageUrl = item.image) }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        CartSummary(subtotal = "$subtotal", shipping = "$shipping", total = "${shipping + subtotal}", navController)
    }
}

fun calculateTotal(cartItems: CartModel): Double {
    return cartItems.cartItems.sumOf {
        val price = it.price?.toDoubleOrNull() ?: 0.0
        val quantity = it.quantity ?: 0
        price * quantity
    }
}

@Composable
fun CartItem(name: String, price: String, quantity: Int, imageUrl: String?) {
    var quantity by remember { mutableStateOf(quantity) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl ?: "",
                contentDescription = name,
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(name, style = MaterialTheme.typography.titleSmall)
                Text(price, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_remove_24),
                    contentDescription = "Decrease",
                    modifier = Modifier.size(20.dp)
                        .clickable {
                            if (quantity > 1) quantity -= 1
                        }
                )
                Text(quantity.toString())
                Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(20.dp)
                    .clickable{
                        quantity += 1
                    })
            }
        }
    }
}

@Composable
fun CartSummary(subtotal: String, shipping: String, total: String, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SummaryRow(label = "Subtotal", value = subtotal)
        SummaryRow(label = "Shipping", value = shipping)
        SummaryRow(label = "Total", value = total, bold = true)

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            onClick = {
                navController.navigate(Route.Dashboard.ORDER_PLACED)
            },
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Proceed to Checkout",
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color.White,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, bold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (bold) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = if (bold) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium
        )
    }
}
