package com.example.picsingular.ui.home.picbed

import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PicBedCard(url: String,belong: String){
    Card(
        modifier = Modifier
            .shadow(elevation = 4.dp)
            .size(width = 168.dp, height = 168.dp)
    ) {
        AsyncImage(model = url, contentDescription = belong)
    }
}