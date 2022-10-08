package com.example.picsingular.ui.components.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SnackBarInfo(snackBarHostState: SnackbarHostState){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        SnackbarHost(hostState = snackBarHostState)
    }
}
