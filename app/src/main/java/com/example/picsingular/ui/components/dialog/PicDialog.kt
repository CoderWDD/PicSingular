package com.example.picsingular.ui.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PicDialog(
    onCameraClick: () -> Unit = {},
    onAlbumClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
){
    Dialog(onDismissRequest = onDismiss) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column (modifier = Modifier.fillMaxWidth()) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = onCameraClick) {
                    Text(text = "拍照")
                }
                Button(modifier = Modifier.fillMaxWidth(), onClick = onAlbumClick) {
                    Text(text = "相册")
                }
            }
        }
    }
}