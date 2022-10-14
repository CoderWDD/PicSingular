package com.example.picsingular.ui.home.release

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.picsingular.R
import com.example.picsingular.common.utils.images.UriTofilePath
import com.example.picsingular.ui.components.dialog.PicDialog
import com.example.picsingular.ui.components.snackbar.SnackBarInfo
import com.example.picsingular.ui.login.LoginViewAction
import com.example.picsingular.ui.theme.BorderColor
import com.example.picsingular.ui.theme.ButtonBackground
import com.example.picsingular.ui.theme.FocusedIndicatorColor
import com.example.picsingular.ui.theme.Grey400
import com.example.picsingular.ui.theme.TransparentColor
import com.example.picsingular.ui.theme.UnfocusedIndicatorColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class)
@Composable
fun ReleasePage(
    navHostController: NavHostController,
    viewModel: ReleaseViewModel = hiltViewModel()
) {
    val imageUrlList = remember { mutableStateListOf<String>() }
    val title = remember { mutableStateOf("") }
    val contentDescription = remember { mutableStateOf("") }
    val releaseState = viewModel.releasePageState
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var cameraUri by remember { mutableStateOf<Uri?>(null) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val albumPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )
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
                val imagePath = UriTofilePath.getFilePathByUri(context, cameraUri)
                imageUrlList.add(imagePath)
            }
        }
    )

    // 打开相册的处理
    val openAlbumLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            if (it == null) return@rememberLauncherForActivityResult
            val imagePath = UriTofilePath.getFilePathByUri(context, it)
            imageUrlList.add(imagePath)
        })

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect { event ->
            when (event) {
                is ReleasePageEvent.CleanSingularEvent -> {
                    // 将页面的信息清空
                    imageUrlList.clear()
                    title.value = ""
                    contentDescription.value = ""
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }

                is ReleasePageEvent.MessageEvent -> snackBarHostState.showSnackbar(message = event.msg)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // title
        Text(text = "发布分享", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        val scrollableState = remember { RowS}
        // 顶部本地图片预览
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(color = Color.White)
                .padding(start = 8.dp),
        ) {
            items(imageUrlList) { imageUrl ->
                AddImageButton(imagePath = imageUrl) {

                }
            }
            item {
                // 添加新图片的按钮
                AddImageButton(isLast = true) {
                    showDialog = true
                }
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BorderColor)
                .height(2.dp)
        )

        // 标题输入框
        TextField(
            value = title.value,
            onValueChange = {
                title.value = it
            },
            placeholder = {
                Text(
                    text = "填写标题会有更多赞哦~",
                    color = MaterialTheme.colors.secondary
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primary,
                textColor = MaterialTheme.colors.onPrimary,
                focusedIndicatorColor = FocusedIndicatorColor,
                unfocusedIndicatorColor = UnfocusedIndicatorColor,
                cursorColor = MaterialTheme.colors.onPrimary
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions { focusManager.clearFocus() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 描述输入框
        TextField(
            value = contentDescription.value,
            onValueChange = { contentDescription.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .border(
                    width = 1.dp,
                    color = FocusedIndicatorColor,
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primary,
                textColor = MaterialTheme.colors.onPrimary,
                unfocusedIndicatorColor = TransparentColor,
                focusedIndicatorColor = TransparentColor,
                cursorColor = MaterialTheme.colors.onPrimary
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions { focusManager.clearFocus() },
            placeholder = {
                Text(text = "给你的分享配上文案吧~", color = MaterialTheme.colors.secondary)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 保存或发布按钮
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(54.dp)
        ) {
            OutlinedButton(
                onClick = {
                    val singularInfo = SingularInfo(
                        content = contentDescription.value,
                        title = title.value,
                        imageUrlList = imageUrlList
                    )
                    viewModel.intentHandler(ReleasePageAction.SaveSingular(singularInfo = singularInfo))
                },
                modifier = Modifier.weight(1f, fill = true),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = TransparentColor
                ),
                border = BorderStroke(width = 1.dp, color = Grey400)
            ) {
                Text(text = "保存")
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(
                onClick = {
                    val singularInfo = SingularInfo(
                        content = contentDescription.value,
                        title = title.value,
                        imageUrlList = imageUrlList
                    )
                    viewModel.intentHandler(ReleasePageAction.ReleaseSingular(singularInfo = singularInfo))
                },
                modifier = Modifier.weight(3f, fill = true),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonBackground,
                    contentColor = Color.White
                )
            ) {
                Text(text = "发布")
            }
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
fun AddImageButton(imagePath: String? = null, isLast: Boolean = false, onClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = BorderColor,
            )
            .size(100.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable {
                onClick.invoke()
            }
    ) {
        val (addIcon, image) = createRefs()
        if (isLast) {
            Icon(painter = painterResource(id = R.drawable.add),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .alpha(0.5f)
                    .constrainAs(addIcon) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })
        } else {
            AsyncImage(model = imagePath,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(16.dp))
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })
        }
    }
}