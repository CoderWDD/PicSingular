package com.example.picsingular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.picsingular.routes.PageNavigationHost
import com.example.picsingular.ui.theme.PicSingularTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PicSingularTheme {
                rememberSystemUiController().setStatusBarColor(color = Color.Transparent)
                val navController = rememberNavController()
                PageNavigationHost(navController)
            }
        }
    }
}









