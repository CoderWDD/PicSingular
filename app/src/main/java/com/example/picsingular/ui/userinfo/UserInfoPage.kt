package com.example.picsingular.ui.userinfo

import android.Manifest
import android.content.ContentValues
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.picsingular.App
import com.example.picsingular.AppState
import com.example.picsingular.R
import com.example.picsingular.common.utils.images.ImageUrlUtil
import com.example.picsingular.common.utils.images.UriTofilePath
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.ui.components.dialog.PicDialog
import com.example.picsingular.ui.components.inputfield.UserInputTextField
import com.example.picsingular.ui.components.snackbar.SnackBarInfo
import com.example.picsingular.ui.login.LoginEvent
import com.example.picsingular.ui.login.LoginViewAction
import com.example.picsingular.ui.login.LoginViewModel
import com.example.picsingular.ui.register.RegisterAction
import com.example.picsingular.ui.theme.Grey200
import com.example.picsingular.ui.theme.H4
import com.example.picsingular.ui.theme.White
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class)
@Composable
fun UserInfo(navHostController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val albumPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )
    PermissionsRequired(
        multiplePermissionsState = albumPermissionsState,
        permissionsNotGrantedContent = { /*TODO*/ },
        permissionsNotAvailableContent = { /*TODO*/ }) {
    }

    // 打开相册的处理
    val openAlbumLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            if (it == null) return@rememberLauncherForActivityResult
            val imagePath = UriTofilePath.getFilePathByUri(context, it)
            viewModel.intentHandler(LoginViewAction.UploadAvatar(imagePath))
        })

    val keyboardController = LocalSoftwareKeyboardController.current
    val snackBarHostState = remember{ SnackbarHostState() }


    LaunchedEffect(Unit){
        viewModel.viewEvent.collect{event ->
            when (event){
                is LoginEvent.MessageEvent -> snackBarHostState.showSnackbar(message = event.msg)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        val appState = App.appState
        val userInfo = appState.userInfo

        // title
        Text(text = "用户信息", fontSize = 24.sp, fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Grey200)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // user avatar
        AsyncImage(
            model = ImageUrlUtil.getAvatarUrl(
                username = userInfo?.username ?: "",
                fileName = userInfo?.avatar ?: ""
            ),
            placeholder = painterResource(id = R.drawable.avatar),
            error = painterResource(id = R.drawable.avatar),
            contentDescription = "User Avatar",
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(230.dp)
                .clickable {
                    if (albumPermissionsState.allPermissionsGranted) {
                        openAlbumLauncher.launch("image/*")
                    } else {
                        albumPermissionsState.launchMultiplePermissionRequest()
                    }
                },
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(16.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Grey200)
                .padding(horizontal = 8.dp)
        )
        var username by remember { mutableStateOf("") }

        TextField(
            value = username,
            singleLine = true,
            onValueChange = { username = it },
            label = {
                Text(
                    text = "Username",
                    color = MaterialTheme.colors.secondary,
                    fontSize = H4
                )
            },
            leadingIcon = { Icon(Icons.Filled.AccountCircle, null) },
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
        var password by remember { mutableStateOf("") }
        var showPassword by remember { mutableStateOf(false) }
        TextField(
            value = password,
            singleLine = true,
            onValueChange = { password = it },
            label = {
                Text(
                    text = "Password",
                    color = MaterialTheme.colors.secondary,
                    fontSize = H4
                )
            },
            leadingIcon = { Icon(Icons.Filled.Lock, null) },
            trailingIcon = {
                IconButton(
                    onClick = { showPassword = !showPassword },
                    modifier = Modifier.size(24.dp)
                ) {
                    if (showPassword) {
                        Icon(painter = painterResource(id = R.drawable.visibility), null)
                    } else {
                        Icon(painter = painterResource(id = R.drawable.visibility_off), null)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .clip(shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                focusedIndicatorColor = MaterialTheme.colors.secondary.copy(alpha = ContentAlpha.high),
            ),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        var signature by remember { mutableStateOf("") }

        TextField(
            value = signature,
            singleLine = true,
            onValueChange = { signature = it },
            label = {
                Text(
                    text = "Signature",
                    color = MaterialTheme.colors.secondary,
                    fontSize = H4
                )
            },
            leadingIcon = { Icon(Icons.Filled.Edit, null) },
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
                viewModel.intentHandler(LoginViewAction.UpdateUserInfo(username = username, password =  password, signature = signature))
            }, modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color(0x5fbdbdbd), shape = RoundedCornerShape(8.dp))
        ) {
            Text(text = "Modifier", color = MaterialTheme.colors.onPrimary, fontSize = H4)
        }
    }
    // 显示event信息
    SnackBarInfo(snackBarHostState = snackBarHostState)
    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier
        .padding(start = 16.dp)
        .size(32.dp)
        .clickable {
            NavHostUtil.navigateBack(navHostController)
        })
}