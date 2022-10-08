package com.example.picsingular.ui.components.drawer

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.picsingular.App
import com.example.picsingular.R
import com.example.picsingular.common.utils.images.ImageUrlUtil
import com.example.picsingular.common.utils.images.UriTofilePath
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.routes.NavRoutes
import com.example.picsingular.ui.components.dialog.PicDialog
import com.example.picsingular.ui.components.snackbar.SnackBarInfo
import com.example.picsingular.ui.login.LoginViewAction
import com.example.picsingular.ui.login.LoginViewModel
import com.google.accompanist.permissions.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Drawer(
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: LoginViewModel = hiltViewModel()
) {
    viewModel.intentHandler(LoginViewAction.InitData)
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val albumPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )
    val scope = rememberCoroutineScope()
    val textList = listOf("图床设置", "PicSingular设置", "关于PicSingular", "退出")
    val iconList = listOf(
        R.drawable.bed_setting,
        R.drawable.pic_singular_setting,
        R.drawable.about,
        R.drawable.logout
    )
    val drawerState = App.appState
    val userInfo = drawerState.userInfo
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var cameraUri by remember { mutableStateOf<Uri?>(null) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    PermissionRequired(
        permissionState = cameraPermissionState,
        permissionNotGrantedContent = { /*TODO*/ },
        permissionNotAvailableContent = { /*TODO*/ }) {
    }

    PermissionsRequired(
        multiplePermissionsState = albumPermissionsState,
        permissionsNotGrantedContent = { /*TODO*/ },
        permissionsNotAvailableContent = { /*TODO*/ }) {
    }

    // 打开相机的处理
    val openCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                imageUri.value = cameraUri
                viewModel.intentHandler(LoginViewAction.UploadAvatar(cameraUri.toString()))
            }
        }
    )

    // 打开相册的处理
    val openAlbumLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            if (it == null) return@rememberLauncherForActivityResult
            imageUri.value = it
            val imagePath = UriTofilePath.getFilePathByUri(context, it)
            viewModel.intentHandler(LoginViewAction.UploadAvatar(imagePath))
        })

    val snackBarHostState = remember { SnackbarHostState() }

    ConstraintLayout {
        val (column) = createRefs()
        Column(
            Modifier
                .background(Color.White)
                .fillMaxSize()
                .constrainAs(column) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (drawerState.isLogin || drawerState.userInfo != null) {
                            // 弹出提示，表示已经登录
                            scope.launch { scaffoldState.drawerState.close() }
                            Toast
                                .makeText(context, "You have been login.", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            // navigate to login page
                            NavHostUtil.navigateTo(
                                navHostController = navHostController,
                                destinationRouteName = NavRoutes.Login.route,
                                singleTop = true
                            )
                            // close drawer
                            scope.launch { scaffoldState.drawerState.close() }
                        }
                    }
                    .padding(top = 40.dp, bottom = 20.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            ) {
                Spacer(modifier = Modifier.width(20.dp))
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
                        .size(80.dp)
                        .clickable {
                            showDialog = !showDialog
                        },
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = if (userInfo?.username.isNullOrEmpty()) "请先登录" else userInfo?.username!!,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (userInfo?.signature.isNullOrEmpty()) "这个人很懒，什么都没有留下" else userInfo?.signature!!,
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                color = MaterialTheme.colors.secondary, modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                for (i in textList.indices) {
                    if (i == 3 && !App.appState.isLogin) {
                        break
                    }
                    DrawerItem(text = textList[i], icon = iconList[i], onClick = {
                        if (i == 3) {
                            viewModel.intentHandler(LoginViewAction.Logout)
                            // 弹出提示，表示已经登录
                            scope.launch { scaffoldState.drawerState.close() }
                            Toast
                                .makeText(context, "Logout success~.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
                }
            }
            Spacer(modifier = Modifier.height(120.dp))
            Divider(
                color = MaterialTheme.colors.secondary, modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 12.dp)
            )
            Text(
                text = "更多功能待开发...",
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 24.dp, top = 24.dp)
            )
        }
    }

    if (showDialog) PicDialog(
        onDismiss = {
            showDialog = !showDialog
        },
        onCameraClick = {
            showDialog = !showDialog
            if (cameraPermissionState.hasPermission) {
                cameraUri = context.contentResolver.insert(
                    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    else MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                    ContentValues()
                )
                openCameraLauncher.launch(cameraUri)
            } else {
                cameraPermissionState.launchPermissionRequest()
            }
        },
        onAlbumClick = {
            showDialog = !showDialog
            if (albumPermissionsState.allPermissionsGranted) {
                openAlbumLauncher.launch("image/*")
            } else {
                albumPermissionsState.launchMultiplePermissionRequest()
            }
        }
    )

    SnackBarInfo(snackBarHostState = snackBarHostState)
}

@Composable
fun DrawerItem(text: String, icon: Int, onClick: () -> Unit) {
    Box(modifier = Modifier
        .height(60.dp)
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .clickable { onClick() }) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = text)
        }
    }
}