package com.example.picsingular.ui.home.picbed

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.picsingular.common.utils.images.ImageUrlUtil

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PicBedCard(url: String, belong: String, onLongClick: ()-> Unit) {
    Card(
        modifier = Modifier
            .shadow(elevation = 4.dp)
            .size(width = 168.dp, height = 168.dp)
            .combinedClickable (
                onLongClick = onLongClick,
                onClick = {}
            )
    ) {
        AsyncImage(
            model = ImageUrlUtil.getPicBedImage(url = url),
            contentDescription = belong,
            modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)).fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}