package com.example.picsingular.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun AboutPicSingularPage(navHostController: NavHostController){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "About me", color= Color.Black)
    }
}