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
import com.example.picsingular.ui.home.NavItem

@Composable
fun MyBottomBar(items: List<NavItem>, navController: NavHostController){
    var selectedItem by remember { mutableStateOf(0) }

    BottomNavigation {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                modifier = Modifier.background(MaterialTheme.colors.primaryVariant),
                selectedContentColor = Color.Black,
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.pageName){
                        // 每次跳转前都把原来的弹出，就可以实现返回直接退出了
                        navController.popBackStack()
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