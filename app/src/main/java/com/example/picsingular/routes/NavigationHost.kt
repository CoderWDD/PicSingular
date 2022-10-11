package com.example.picsingular.routes

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.picsingular.bean.Singular
import com.example.picsingular.bean.User
import com.example.picsingular.common.constants.RouteConstants
import com.example.picsingular.common.utils.navhost.fromJson
import com.example.picsingular.ui.details.SingularDetailPage
import com.example.picsingular.ui.home.HomePage
import com.example.picsingular.ui.home.picbed.PicBedPage
import com.example.picsingular.ui.home.community.CommunityPage
import com.example.picsingular.ui.home.community.subscription.userhome.UserHomePage
import com.example.picsingular.ui.login.LoginPage
import com.example.picsingular.ui.home.release.ReleasePage
import com.example.picsingular.ui.register.RegisterPage
import com.example.picsingular.ui.splash.SplashPage

@Composable
fun HomeNavigationHost(navHostController: NavHostController,scaffoldState: ScaffoldState){
    NavHost(navController = navHostController, startDestination = NavRoutes.CommunityPage.route, builder = {
        composable(route = NavRoutes.CommunityPage.route, content = { CommunityPage(navHostController = navHostController, scaffoldState = scaffoldState) })
        composable(route = NavRoutes.Release.route, content = { ReleasePage(navHostController = navHostController) })
        composable(route = NavRoutes.PicBedPage.route, content = { PicBedPage(navController = navHostController) })

        composable(route = NavRoutes.Login.route, content = { LoginPage(navHostController = navHostController) })
        composable(route = NavRoutes.Home.route, content = { HomePage() })

        composable(route = NavRoutes.SingularDetailPage.route + "/{${RouteConstants.SINGULAR_DATA}}", arguments = listOf(
            navArgument(RouteConstants.SINGULAR_DATA){
                nullable = false
                type = NavType.StringType
            },),
        ){
            val singularData = it.arguments?.getString(RouteConstants.SINGULAR_DATA)?.fromJson<Singular>()
            SingularDetailPage(navHostController = navHostController, scaffoldState = scaffoldState, singularData = singularData!!)
        }

        composable(route = NavRoutes.UserHomePage.route + "/{${RouteConstants.USER_INFO}}", arguments = listOf(
            navArgument(RouteConstants.USER_INFO){
                nullable = false
                type = NavType.StringType
            }
        )){
            val userData = it.arguments?.getString(RouteConstants.USER_INFO)?.fromJson<User>()
            UserHomePage(navHostController = navHostController, userData = userData)
        }

        composable(route = NavRoutes.RegisterPage.route, content = { RegisterPage(navHostController = navHostController)})

        composable(route = NavRoutes.PicBedSettingPage.route, content = {})

        composable(route = NavRoutes.PicSingularSettingPage.route, content = {})

        composable(route = NavRoutes.AboutPicSingularPage.route, content = {})
    })
}

