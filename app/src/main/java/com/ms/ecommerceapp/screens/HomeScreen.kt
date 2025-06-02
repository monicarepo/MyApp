package com.ms.ecommerceapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ms.apimockdata.model.CartModel
import com.ms.apimockdata.model.ProductListModel
import com.ms.apptheme.route.Route
import com.ms.apptheme.ui.theme.AppTypography
import com.ms.ecommerceapp.R
import com.ms.ecommerceapp.navigation.BottomNavItem

@Composable
fun HomeScreen(
    productList: List<ProductListModel>,
    cartItems: CartModel,
    navController: NavHostController
) {
    var selectedCategory by remember { mutableStateOf("Electronics") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredList = productList.filter {
        it.category.equals(selectedCategory, ignoreCase = true) && (searchQuery.isBlank() || it.name?.contains(searchQuery, ignoreCase = true) == true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            cartItems,
            onCartClick = {
                navController.navigate(BottomNavItem.Cart.route)
            }
        )
        SearchBar(searchQuery, onQueryChange = { searchQuery = it })
        PromoBanner()
        CategoryRow(selectedCategory = selectedCategory, onCategorySelected = {
            selectedCategory = it
        })
        FeaturedProductList(filteredList)
    }
}

@Composable
fun TopBar(cartItems: CartModel, onCartClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Welcome, User 👋", style = MaterialTheme.typography.headlineLarge)
        BadgedBox(
            badge = {
                if (cartItems.cartItems.size > 0) {
                    Badge { Text(cartItems.cartItems.size.toString()) }
                }
            }
        ) {
            IconButton(onClick = onCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun SearchBar(searchQuery: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        placeholder = { Text("Search products") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun PromoBanner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        /***
         * SubcomposeAsyncImage allows you to customize the UI based on loading state — such as showing a progress indicator while loading, an error view on failure, etc.
         * It’s more flexible and powerful, especially when you want to render different Composables for each state.
         * ***/
        SubcomposeAsyncImage(
            model = "https://media.istockphoto.com/id/1194343598/vector/bright-modern-mega-sale-banner-for-advertising-discounts-vector-template-for-design-special.jpg?s=1024x1024&w=is&k=20&c=0mCYIySv67QfDOLtd5Emkkf4exK-JLRWBKWNML0zeAs=",
            contentDescription = "Banner",
            contentScale = ContentScale.Crop,
            loading = {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            },
            modifier = Modifier.fillMaxWidth()
                .height(180.dp)
        )
    }
}

@Composable
fun CategoryRow(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(listOf("Men", "Women", "Kids", "Electronics", "Appliances")) { category ->
            Chip(
                text = category,
                isSelected = category == selectedCategory,
                onSelectedChange = { selected ->
                    if (selected) {
                        onCategorySelected(category)
                    }
                })
        }
    }
}

@Composable
fun Chip(text: String, isSelected: Boolean,  onSelectedChange: (Boolean) -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
//        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        onClick = { onSelectedChange(true)}
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            style = AppTypography.bodyMedium
        )
    }
}


@Composable
fun FeaturedProductList(productList: List<ProductListModel>) {
    if(productList.isEmpty()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No products available",
                style = AppTypography.bodyMedium,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(productList) { item ->
                ProductItem(
                    name = item.name ?: "product",
                    price = item.price.toString(),
                    imageUrl = item.image
                )
            }
        }
    }
}

@Preview
@Composable
fun LoadingImageFromInternetCoil() {
    AsyncImage(
        model = "https://picsum.photos/id/237/200/300",
        contentDescription = "Translated description of what the image contains"
    )
}

@Composable
fun NetworkImageDemo() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://picsum.photos/id/237/200/300")
            .listener(
                onError = { _, throwable ->
                    Log.e("Coil", "Load failed", throwable.throwable)
                },
                onSuccess = { _, _ ->
                    Log.d("Coil", "Load succeeded")
                }
            )
            .build(),
        contentDescription = "Demo image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_launcher_background),
        error = painterResource(R.drawable.ic_launcher_background)
    )
}

@Composable
fun ProductItem(name: String, price: String, imageUrl: String?) {

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl ?: "https://picsum.photos/200")
                    .crossfade(true)
                    .listener(
                        onError = { _, throwable ->
                            Log.e("ImageLoad", "Failed: ${throwable.throwable.message}")
                        },
                        onSuccess = { _, _ ->
                            Log.d("ImageLoad", "Loaded image successfully")
                        }
                    )
                    .build(),
                contentDescription = name,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                error = painterResource(id = R.drawable.ic_launcher_background),
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(name, style = MaterialTheme.typography.titleSmall)
                Text(price, style = AppTypography.bodyMedium, color = Color.Gray)
            }
        }
    }
}
