package com.example.picsingular.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.picsingular.ui.components.snackbar.SnackBarInfo
import kotlinx.coroutines.launch

@Composable
fun SplashPage(invokeFunction: () -> Unit) {
    val snackBarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                invokeFunction()
            },
            modifier = Modifier.background(color = Color.Black)
        ) {

        }

        val scope = rememberCoroutineScope()

        Button(onClick = {
            snackBarHostState.currentSnackbarData
            scope.launch { snackBarHostState.showSnackbar(message = "snack bar test", actionLabel = "Cancel") }
        }) {
            Text(text = "show snack bar")
        }
    }
    SnackBarInfo(snackBarHostState = snackBarHostState)
}


