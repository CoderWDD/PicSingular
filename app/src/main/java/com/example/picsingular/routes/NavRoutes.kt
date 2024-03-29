package com.example.picsingular.routes

sealed class NavRoutes(val route: String) {
    object PicBedPage : NavRoutes("PicBedPage")
    object CommunityPage : NavRoutes("CommunityPage")
    object Release : NavRoutes("ReleasePage")
    object Login : NavRoutes("LoginPage")
    object Home : NavRoutes("HomePage")
    object SingularDetailPage: NavRoutes("SingularDetailPage")
    object UserHomePage: NavRoutes("UserHomePage")
    object RegisterPage: NavRoutes("RegisterPage")
    object PicBedSettingPage: NavRoutes("PicBedSettingPage")
    object UserInfoPage: NavRoutes("UserInfoPage")
    object AboutPicSingularPage: NavRoutes("AboutPicSingularPage")
}