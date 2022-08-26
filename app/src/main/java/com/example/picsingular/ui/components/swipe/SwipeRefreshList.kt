package com.example.picsingular.ui.components.swipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun <T: Any> SwipeRefreshList (
    lazyPagingItems: LazyPagingItems<T>,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit) = {},
    listState: LazyListState = rememberLazyListState(),
    itemContent: LazyListScope.() -> Unit
){
    // swipeRefresh 支持下拉刷新
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    SwipeRefresh(
        state = swipeRefreshState,
        modifier = Modifier
            .fillMaxSize(),
        onRefresh = {
            onRefresh()
            lazyPagingItems.refresh()
        }
    ) {
        // 将刷新的状态更新到swipeRefresh中
        swipeRefreshState.isRefreshing = isRefreshing || lazyPagingItems.loadState.refresh is LoadState.Loading
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            itemContent()
            // 上拉刷新底部的信息
            if (!swipeRefreshState.isRefreshing){
                item {
                    lazyPagingItems.apply {
                        when(loadState.append) {
                            is LoadState.Loading -> LoadingItem()
                            is LoadState.Error -> ErrorItem(retry = { retry() })
                            is LoadState.NotLoading -> {
                                if (loadState.append.endOfPaginationReached){
                                    NoMoreItem()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoMoreItem(){
    Text("没有更多了",textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
}

@Composable
fun LoadingItem(){
    Text("加载中...",textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
}

@Composable
fun ErrorItem(retry: () -> Unit){
    Text(
        "加载失败，点击重试",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().clickable { retry() }
    )
}