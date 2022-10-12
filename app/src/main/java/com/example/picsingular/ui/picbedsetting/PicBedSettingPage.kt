package com.example.picsingular.ui.picbedsetting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.ui.components.snackbar.SnackBarInfo
import com.example.picsingular.ui.login.LoginViewAction
import com.example.picsingular.ui.theme.Grey200
import com.example.picsingular.ui.theme.H4
import com.example.picsingular.ui.theme.White

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PicBedSettingPage(navHostController: NavHostController, viewModel: PicBedSettingViewModel = hiltViewModel()){
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackBarHostState = remember{ SnackbarHostState() }
    val viewState = viewModel.viewState
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        viewModel.intentHandler(PicBedSettingAction.GetPicBedConfig)
        // title
        Text(text = "图床设置", fontSize = 24.sp, fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Grey200)
        )

        Spacer(modifier = Modifier.height(8.dp))

        var basePicBedUrl by remember { mutableStateOf(viewState.baseUrl) }

        TextField(
            value = basePicBedUrl,
            singleLine = true,
            onValueChange = { basePicBedUrl = it },
            label = {
                Text(
                    text = "PicBedUrl",
                    color = MaterialTheme.colors.secondary,
                    fontSize = H4
                )
            },
            leadingIcon = { Icon(Icons.Filled.Settings, null) },
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .clip(shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                focusedIndicatorColor = MaterialTheme.colors.secondary.copy(alpha = ContentAlpha.high),
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        var picBedToken by remember { mutableStateOf(viewState.token) }

        TextField(
            value = picBedToken,
            singleLine = true,
            onValueChange = { picBedToken = it },
            label = {
                Text(
                    text = "PicBedToken",
                    color = MaterialTheme.colors.secondary,
                    fontSize = H4
                )
            },
            leadingIcon = { Icon(Icons.Filled.Lock, null) },
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .clip(shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                focusedIndicatorColor = MaterialTheme.colors.secondary.copy(alpha = ContentAlpha.high),
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                keyboardController?.hide()
                viewModel.intentHandler(PicBedSettingAction.SavePicBedConfig(basePicBedUrl, picBedToken))
            }, modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color(0x5fbdbdbd), shape = RoundedCornerShape(8.dp))
        ) {
            Text(text = "Save", color = MaterialTheme.colors.onPrimary, fontSize = H4)
        }
    }

    // 显示 SnackBar 信息
    SnackBarInfo(snackBarHostState = snackBarHostState)

    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier
        .padding(start = 16.dp)
        .size(32.dp)
        .clickable {
            NavHostUtil.navigateBack(navHostController)
        })
}