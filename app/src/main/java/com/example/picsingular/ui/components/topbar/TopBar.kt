package com.example.picsingular.ui.components.topbar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun MyTopBar(appTitle: String,scaffoldState: ScaffoldState){
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = { Text(text = appTitle) },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        elevation = 8.dp,
        navigationIcon = {
            IconButton(onClick = {
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
        }
    )
}