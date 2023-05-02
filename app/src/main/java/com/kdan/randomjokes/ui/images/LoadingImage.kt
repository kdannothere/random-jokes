package com.kdan.randomjokes.ui.images

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.kdan.randomjokes.R

@Composable
fun LoadingImage() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        modifier = Modifier.size(100.dp),
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = R.drawable.loading_spinner).apply(
                block = {
                    size(Size.ORIGINAL)
                }).build(),
            imageLoader = imageLoader
        ),
        contentDescription = "Image loading spinner"
    )
}