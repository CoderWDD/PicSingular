package com.example.picsingular.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.picsingular.ui.home.HomePage
import com.example.picsingular.ui.home.picbed.PicBedPage
import com.example.picsingular.ui.home.community.CommunityPage
import com.example.picsingular.ui.login.LoginPage
import com.example.picsingular.ui.home.release.ReleasePage
import com.example.picsingular.ui.splash.SplashPage

@Composable
fun HomeNavigationHost(navController: NavHostController, pageNavHostController: NavHostController){
    NavHost(navController = navController, startDestination = NavRoutes.CommunityPage.route, builder = {
        composable(route = NavRoutes.CommunityPage.route, content = { CommunityPage(navController = navController) })
        composable(route = NavRoutes.Release.route, content = { ReleasePage(navController = navController) })
        composable(route = NavRoutes.PicBedPage.route, content = { PicBedPage(navController = navController) })
    })
}

@Composable
fun PageNavigationHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = NavRoutes.Home.route, builder = {
        composable(route = NavRoutes.Login.route, content = { LoginPage(navController = navController) })
        composable(route = NavRoutes.Splash.route, content = { SplashPage(navController = navController) })
        composable(route = NavRoutes.Home.route, content = { HomePage(navController) })
    })
}