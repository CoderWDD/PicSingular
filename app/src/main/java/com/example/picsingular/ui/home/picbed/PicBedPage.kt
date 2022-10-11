package com.example.picsingular.ui.home.picbed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.picsingular.ui.theme.Grey200


@Composable
fun PicBedPage(navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .pointerInput(Unit) {
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // title
        Text(text = "图床", fontSize = 24.sp, fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .background(color = Grey200))

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
}