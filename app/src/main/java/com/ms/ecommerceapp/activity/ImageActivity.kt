package com.ms.ecommerceapp.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.imageLoader
import coil3.memory.MemoryCache
import com.ms.apptheme.ui.theme.AppTheme

class ImageActivity : ComponentActivity() {
    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageUrl = "https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg"
//        val imageUrl = "https://picsum.photos/300/200"
        val imageUrl2 = "https://cdn.pixabay.com/photo/2017/01/29/14/19/kermit-2018085_1280.jpg"
//        val imageUrl2 = "https://cdn.pixabay.com/photo/2017/01/29/14/19/kermit-2018085_1280.jpg"
        setContent {
            AppTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1280f/847f)
                    )
                    AsyncImage(
                        model = imageUrl2,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1280f/692f),
                        onState = { state ->
                            when (state) {
                                is AsyncImagePainter.State.Success -> {
                                    Log.d("Coil", "Load succeeded")
                                }
                                is AsyncImagePainter.State.Error -> {
                                    Log.e("Coil", "Load failed", state.result.throwable)
                                }
                                else -> {} // Ignore loading/empty states
                            }
                        }
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            imageLoader.diskCache?.remove(imageUrl)
                            imageLoader.memoryCache?.remove(MemoryCache.Key(imageUrl))
                        }
                    ) {
                        Text("Clear cache")
                    }
                }
            }
        }
    }
}