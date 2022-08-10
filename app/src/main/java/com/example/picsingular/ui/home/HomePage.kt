package com.example.picsingular.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.picsingular.routes.HomeNavigationHost
import com.example.picsingular.routes.NavRoutes
import com.example.picsingular.ui.components.bottombar.MyBottomBar
import com.example.picsingular.ui.components.drawer.Drawer
import com.example.picsingular.ui.components.topbar.MyTopBar

@Composable
fun HomePage(pageNavHostController: NavHostController){
    MyScaffold(pageNavHostController)
}

data class NavItem(val itemName: String,val pageName: String)

@Composable
fun MyScaffold(pageNavHostController: NavHostController) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val items = listOf(
        NavItem(itemName =  "社区", pageName = NavRoutes.CommunityPage.route),
        NavItem(itemName =  "发布", pageName = NavRoutes.Release.route),
        NavItem(itemName = "图床", pageName = NavRoutes.PicBedPage.route),
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MyTopBar(appTitle = "PicSingular", scaffoldState = scaffoldState)
        },
        bottomBar = {
            MyBottomBar(items = items,navController = navController)
        },
        drawerContent = {
            Drawer(pageNavHostController = pageNavHostController)
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            HomeNavigationHost(navController = navController, pageNavHostController = pageNavHostController)
        }
    }
}
