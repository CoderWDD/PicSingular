package com.example.picsingular.ui.splash

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.picsingular.routes.NavRoutes

@Composable
fun SplashPage(navController: NavController){
    Button(
        onClick = {
            navController.navigate(NavRoutes.Home.route){
                // 每次跳转前都把原来的弹出，就可以实现返回直接退出了
                navController.popBackStack()
            }
        },

    ) {

    }
}