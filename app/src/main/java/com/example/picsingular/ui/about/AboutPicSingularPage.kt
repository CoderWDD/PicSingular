package com.example.picsingular.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.picsingular.ui.theme.H2
import com.example.picsingular.ui.theme.H3
import com.example.picsingular.ui.theme.H4
import com.example.picsingular.ui.theme.H5

@Composable
fun AboutPicSingularPage(navHostController: NavHostController){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "About me", color= Color.Black, fontSize = H3)
        Text(text = "    This is a picture share platform made by Compose, Use Kotlin as the main develop language instead of java.")
        Text(text = "    Integrated common picture share functions and pictureBed module. You can share your favourite images with other users and exhibit your personal image collection in your PicBed.")
        Text(text = "github link")
        Text(text = "This Project is code & design with love")
        Text(text = "by CoderWdd & Lorde")
    }
}