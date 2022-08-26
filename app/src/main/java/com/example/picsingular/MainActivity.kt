package com.example.picsingular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import com.example.picsingular.ui.home.HomePage
import com.example.picsingular.ui.splash.SplashPage
import com.example.picsingular.ui.theme.PicSingularTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isSplash by remember { mutableStateOf(true) }
            PicSingularTheme {
                if (isSplash){
                    SplashPage{isSplash = false}
                }else{
                    HomePage()
                }
            }
        }
    }



}









