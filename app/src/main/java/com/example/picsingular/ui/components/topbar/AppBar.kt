package com.example.picsingular.ui.components.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.picsingular.ui.home.community.Page
import kotlinx.coroutines.launch

@Composable
fun CommunityTabBar(
    pagesList: List<Page>,
    currentTab: Int,
    scaffoldState: ScaffoldState,
    onTabSelected: (Int) -> Unit
){
    val scope = rememberCoroutineScope()
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ){
        // drawer call icon
        IconButton(
            onClick = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "",
                tint = Color.Black
            )
        }

        // top bar
        ScrollableTabRow(
            selectedTabIndex = currentTab
        ) {
            pagesList.forEachIndexed { index,page ->
                Tab(selected = index == currentTab, onClick = { onTabSelected(index) }, text = { Text(text = page.title) })
            }
        }

    }
}