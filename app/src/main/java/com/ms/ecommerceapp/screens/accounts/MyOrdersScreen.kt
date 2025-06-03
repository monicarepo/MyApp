package com.ms.ecommerceapp.screens.accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.ms.apimockdata.model.CartItemModel
import com.ms.apimockdata.model.OrderDetailModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen(
    orderList: List<OrderDetailModel>, navController: NavHostController,
    onReorderClick: (OrderDetailModel) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("My Orders") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(modifier = Modifier.padding(16.dp)) {

            Spacer(modifier = Modifier.height(8.dp))

            if (orderList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No orders found.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(orderList) { order ->
                        OrderCard(order = order, onReorderClick)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: OrderDetailModel, onReorderClick: (OrderDetailModel) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Order #${order.orderId}", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = order.status ?: "Pending",
                    color = when (order.status) {
                        "Delivered" -> Color(0xFF4CAF50)
                        "Shipped" -> Color(0xFF2196F3)
                        else -> Color.Gray
                    },
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                "Total: $${String.format("%.2f", order.totalPrice ?: 0.0)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                order.cartItems.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        AsyncImage(
                            model = item.image ?: "",
                            contentDescription = item.name ?: "",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(item.name ?: "", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "Qty: ${item.quantity ?: 0} • $${item.price ?: "0.00"}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onReorderClick(order) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Reorder")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyOrdersPreview() {
    val dummyOrders = listOf(
        OrderDetailModel(
            orderId = 101,
            userId = 1,
            status = "Delivered",
            totalPrice = 1249.98,
            cartItems = arrayListOf(
                CartItemModel(
                    productId = 1,
                    quantity = 1,
                    name = "Smartphone",
                    price = "599.99",
                    image = "https://img.freepik.com/free-vector/mobile-phone-isolated-white-background_1284-47076.jpg"
                ),
                CartItemModel(
                    productId = 2,
                    quantity = 1,
                    name = "Headphones",
                    price = "649.99",
                    image = "https://media.istockphoto.com/id/1372906882/photo/modern-blue-wireless-headphones-isolated.jpg"
                )
            )
        ),
        OrderDetailModel(
            orderId = 102,
            userId = 2,
            status = "Shipped",
            totalPrice = 599.99,
            cartItems = arrayListOf(
                CartItemModel(
                    productId = 3,
                    quantity = 1,
                    name = "Dress",
                    price = "599.99",
                    image = "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcQvdCp1mHpK-TI8TF_qbsad2nOV3ah4tpSgs5bDsLj"
                )
            )
        )
    )

    val navController = rememberNavController()
    MyOrdersScreen(orderList = dummyOrders, navController = navController )
}
