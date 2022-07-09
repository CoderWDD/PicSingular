package com.example.picsingular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.picsingular.ui.theme.PicSingularTheme
import com.example.picsingular.ui.theme.TextPrimaryColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PicSingularTheme {
                rememberSystemUiController().setStatusBarColor(color = Color.Transparent)
                MyScaffold()
            }
        }
    }
}

@Preview
@Composable
fun MyScaffold() {
    val scaffoldState = rememberScaffoldState()
    val items = listOf("图床", "社区")

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MyTopBar(appTitle = "PicSingular", scaffoldState = scaffoldState)
        },
        bottomBar = {
            MyBottomBar(items = items)
        },
        drawerContent = {
            MyDrawer()
        }
    ) {
        Surface(
            modifier = Modifier.padding(it),
        ) {
            Text(text = "help", color = TextPrimaryColor)
        }
    }
}

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

@Preview
@Composable
fun MyDrawer(){
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        repeat(5) { item ->
            Text(text = "Item $item", modifier = Modifier.padding(8.dp), color = Color.Black)
        }
    }
}

@Composable
fun MyBottomBar(items: List<String>){
    var selectedItem by remember { mutableStateOf(0) }
    BottomNavigation() {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                modifier = Modifier.background(MaterialTheme.colors.primaryVariant),
                selectedContentColor = Color.Black,
                selected = selectedItem == index,
                onClick = { selectedItem = index }, label = { Text(text = item, color = Color.Black) },
                icon = {
                    when (index) {
                        0 -> Icon(
                            imageVector = if (selectedItem == index) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = item,
                            tint = if (selectedItem == index) Color.Black else MaterialTheme.colors.secondary
                        )
                        1 -> Icon(
                            imageVector = if (selectedItem == index) Icons.Filled.Create else Icons.Outlined.Create,
                            contentDescription = item,
                            tint = if (selectedItem == index) Color.Black else MaterialTheme.colors.secondary
                        )
                    }
                })
        }
    }
}