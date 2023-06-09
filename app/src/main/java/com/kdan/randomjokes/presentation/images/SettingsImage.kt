package com.kdan.randomjokes.presentation.images

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.kdan.randomjokes.presentation.utility.Utility

@Composable
fun SettingsImage(
    modifier: Modifier,
    showSettingDialog: MutableState<Boolean>,
    darkMode: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    val image = if (darkMode.value) {
        R.drawable.settings_dark
    } else R.drawable.settings
    Image(
        modifier = modifier
            .size(50.dp)
            .clickable { Utility.turnOnDialog(showSettingDialog) },
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = image).apply(
                block = {
                    size(Size.ORIGINAL)
                }).build(),
            imageLoader = imageLoader
        ),
        contentDescription = "Image button settings"
    )
}