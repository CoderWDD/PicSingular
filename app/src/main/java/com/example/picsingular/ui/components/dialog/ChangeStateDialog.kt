package com.example.picsingular.ui.components.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ChangeStateDialog(
    isShared: Boolean = false,
    onDismissClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onConfirmClick) {
                Text(text = "确定")
            }
        } ,
        dismissButton = {
            Button(onClick = onDismissClick) {
                Text(text = "取消")
            }
        },
        title = {
            Text(text = "提示")
        },
        text = {
            Text(text = if (isShared) "是否将其取消分享？" else "是否将其公开分享？")
        }
    )
}