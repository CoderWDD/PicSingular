package com.example.picsingular.ui.components.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DeletePicBedImageDialog(
    delete: Boolean = true,
    onDismissClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onConfirmClick) {
                Text(text = "删除")
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
            Text(text = "是否将其删除？")
        }
    )
}