import android.util.Log
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ms.ecommerceapp.R

@Composable
fun ImageLoaderScreen() {
    val imageUrl = "https://raw.githubusercontent.com/coil-kt/coil/refs/heads/main/internal/test-utils/src/androidMain/assets/normal.jpg"

    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1280f/1280f)
    )

//    AsyncImage(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data("https://raw.githubusercontent.com/coil-kt/coil/refs/heads/main/internal/test-utils/src/androidMain/assets/normal.jpg")
//            .crossfade(true)
//            .listener(
//                onError = { _, throwable ->
//                    Log.e("Coil", "Load failed", throwable.throwable)
//                },
//                onSuccess = { _, _ ->
//                    Log.d("Coil", "Load succeeded")
//                }
//            )
//            .build(),
//        contentDescription = "Demo image",
//        modifier = Modifier.size(200.dp),
//        placeholder = painterResource(R.drawable.ic_launcher_background),
//        error = painterResource(R.drawable.ic_launcher_background)
//    )
}
