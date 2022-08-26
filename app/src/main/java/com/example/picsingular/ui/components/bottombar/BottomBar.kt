package com.example.picsingular.ui.components.bottombar

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.picsingular.routes.NavRoutes
import com.example.picsingular.ui.home.NavItem

@Composable
fun MyBottomBar(items: List<NavItem>, navHostController: NavHostController){
    var selectedItem by remember { mutableStateOf(0) }

    // find the current route and if it is not in the list of routes, just return
    // this can make the bottom bar not work if the current route is not in the list of routes
    val currentBackStackEntry = navHostController.currentBackStackEntryAsState()
    val destination = currentBackStackEntry.value?.destination
    val bottomNavRoutesList: List<String> = items.map { it.pageName }
    if (!bottomNavRoutesList.contains(destination?.route)){
        return
    }

    BottomNavigation {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                modifier = Modifier.background(MaterialTheme.colors.primaryVariant),
                selectedContentColor = Color.Black,
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navHostController.navigate(item.pageName){
                        // 每次跳转前都把原来的弹出，就可以实现返回直接退出了
                        navHostController.popBackStack()
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(text = item.itemName, color = Color.Black) },
                icon = {
                    when (index) {
                        0 -> Icon(
                            imageVector = if (selectedItem == index) Icons.Filled.Create else Icons.Outlined.Create,
                            contentDescription = item.itemName,
                            tint = if (selectedItem == index) Color.Black else MaterialTheme.colors.secondary
                        )
                        1 -> Icon(
                            imageVector = if (selectedItem == index) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = item.itemName,
                            tint = if (selectedItem == index) Color.Black else MaterialTheme.colors.secondary
                        )
                        2 -> Icon(
                            imageVector = if (selectedItem == index) Icons.Filled.DateRange else Icons.Outlined.DateRange,
                            contentDescription = item.itemName,
                            tint = if (selectedItem == index) Color.Black else MaterialTheme.colors.secondary
                        )
                    }
                })
        }
    }
}