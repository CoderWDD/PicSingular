package com.example.picsingular.routes

sealed class NavRoutes(val route: String) {
    object PicBedPage : NavRoutes("PicBedPage")
    object CommunityPage : NavRoutes("CommunityPage")
    object Release : NavRoutes("ReleasePage")

    object Login : NavRoutes("LoginPage")
    object Splash : NavRoutes("SplashPage")
    object Home : NavRoutes("HomePage")
}