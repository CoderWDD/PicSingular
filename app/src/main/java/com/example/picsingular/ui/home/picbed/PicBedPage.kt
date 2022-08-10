package com.example.picsingular.ui.home.picbed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun PicBedPage(navController: NavController){
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        contentPadding = PaddingValues(12.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = {
        items(12){
            PicBedCard(url = "", belong = "")
        }
    })
} 