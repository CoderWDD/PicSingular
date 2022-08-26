package com.example.picsingular.ui.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.picsingular.routes.HomeNavigationHost
import com.example.picsingular.routes.NavRoutes
import com.example.picsingular.ui.components.bottombar.MyBottomBar
import com.example.picsingular.ui.components.drawer.Drawer
import com.example.picsingular.ui.components.topbar.MyTopBar
import com.example.picsingular.ui.login.LoginViewAction
import com.example.picsingular.ui.login.LoginViewModel

@Composable
fun HomePage(viewModel: LoginViewModel = hiltViewModel()){
    viewModel.intentHandler(LoginViewAction.GetUserInfo)
    MyScaffold()
}

data class NavItem(val itemName: String,val pageName: String)

@Composable
fun MyScaffold() {
    val navHostController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val items = listOf(
        NavItem(itemName =  "社区", pageName = NavRoutes.CommunityPage.route),
        NavItem(itemName =  "发布", pageName = NavRoutes.Release.route),
        NavItem(itemName = "图床", pageName = NavRoutes.PicBedPage.route),
    )

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            MyBottomBar(items = items,navHostController = navHostController)
        },
        drawerContent = {
            Drawer(navHostController = navHostController, scaffoldState = scaffoldState)
        }
    ) {
        scaffoldState.snackbarHostState
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            HomeNavigationHost(navHostController = navHostController,scaffoldState = scaffoldState)
        }
    }
}


