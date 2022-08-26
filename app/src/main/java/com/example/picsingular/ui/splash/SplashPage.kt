package com.example.picsingular.ui.splash

import androidx.compose.foundation.background
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.picsingular.routes.NavRoutes

@Composable
fun SplashPage(invokeFunction: () -> Unit){

    Button(
        onClick = {
            invokeFunction()
        },
        modifier = Modifier.background(color = Color.Black)
    ) {

    }
}