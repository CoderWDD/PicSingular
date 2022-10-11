package com.example.picsingular.ui.home.community.shared

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.picsingular.bean.Singular
import com.example.picsingular.ui.components.dialog.ChangeStateDialog
import com.example.picsingular.ui.components.items.singular.SingularItem
import com.example.picsingular.ui.components.swipe.SwipeRefreshListColumn

@Composable
fun SharedPage(
    navHostController: NavHostController,
    viewModel: SharedViewModel = hiltViewModel()
){
    val sharedViewState = viewModel.sharedViewState
    val pageDataList = sharedViewState.pageDataList.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    var showDialog by remember { mutableStateOf(false) }
    var singularId by remember { mutableStateOf(-1L) }

    SwipeRefreshListColumn(
        lazyPagingItems = pageDataList,
        listState = listState
    ){
        itemsIndexed(pageDataList){_: Int, item: Singular? ->
            SingularItem(singularData = item!!, navHostController = navHostController, isSelf = true, onLongClick = {
                showDialog = true
                singularId = item.singularId
            })
        }
    }

    if (showDialog){
        ChangeStateDialog(
            isShared = true,
            onConfirmClick = {
                showDialog = false
                viewModel.intentHandler(SharedViewAction.ChangeToSaved(singularId))
            },
            onDismissClick = {
                showDialog = false
            },
            onDismissRequest = {
                showDialog = false
            }
        )
    }
}

