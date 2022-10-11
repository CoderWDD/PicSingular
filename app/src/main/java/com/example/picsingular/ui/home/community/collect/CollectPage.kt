package com.example.picsingular.ui.home.community.collect

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.picsingular.bean.Singular
import com.example.picsingular.ui.components.items.singular.SingularItem
import com.example.picsingular.ui.components.swipe.SwipeRefreshListColumn

@Composable
fun CollectPage(
    navHostController: NavHostController,
    viewModel: CollectViewModel = hiltViewModel()
){
    val collectViewState = viewModel.favoriteViewState
    val pageDataList = collectViewState.pagingDataList.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    SwipeRefreshListColumn(lazyPagingItems = pageDataList, listState = listState){
        itemsIndexed(pageDataList){_: Int, item: Singular? ->
            SingularItem(singularData = item!!, navHostController = navHostController, hasFavorite = true)
        }
    }
}