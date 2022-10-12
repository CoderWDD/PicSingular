package com.example.picsingular.ui.home.picbed

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Divider
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.picsingular.R
import com.example.picsingular.common.utils.images.UriTofilePath
import com.example.picsingular.ui.components.dialog.PicDialog
import com.example.picsingular.ui.components.snackbar.SnackBarInfo
import com.example.picsingular.ui.components.swipe.SwipeRefreshListGrid
import com.example.picsingular.ui.picbedsetting.PicBedSettingAction
import com.example.picsingular.ui.picbedsetting.PicBedSettingViewModel
import com.example.picsingular.ui.theme.ButtonBackground
import com.example.picsingular.ui.theme.Grey200
import com.example.picsingular.ui.theme.Grey400
import com.example.picsingular.ui.theme.LoginButtonBackground
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlin.math.log


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PicBedPage(
    navController: NavController,
    viewModel: PicBedViewModel = hiltViewModel(),
    picBedSettingViewModel: PicBedSettingViewModel = hiltViewModel()
) {

    // 获取图床设置
    picBedSettingViewModel.intentHandler(PicBedSettingAction.GetPicBedConfig)
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
                viewModel.intentHandler(PicBedPageAction.UploadImage(cameraUri.toString()))
            }
        }
    )

    // 打开相册的处理
    val openAlbumLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            if (it == null) return@rememberLauncherForActivityResult
            val imagePath = UriTofilePath.getFilePathByUri(context, it)
            viewModel.intentHandler(PicBedPageAction.UploadImage(imagePath))
        })

    val snackBarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val picBedPageState = viewModel.picBedPageState
        val picBedPageList = picBedPageState.pageDataList.collectAsLazyPagingItems()
        val gridState = rememberLazyGridState()

        // title
        Text(text = "图床", fontSize = 24.sp, fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Grey200)
        )

        SwipeRefreshListGrid(lazyPagingItems = picBedPageList, gridState = gridState){
            items(picBedPageList.itemCount) {position ->
                PicBedCard(url = picBedPageList[position] ?: "", belong = "")
            }
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val fab = createRef()
        FloatingActionButton(onClick = { showDialog = true }, backgroundColor = ButtonBackground, modifier = Modifier.constrainAs(fab){
            bottom.linkTo(parent.bottom, margin = 16.dp)
            end.linkTo(parent.end, margin = 8.dp)
        }) {
            Icon(painter = painterResource(id = R.drawable.add), contentDescription = null, modifier = Modifier.size(36.dp))
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