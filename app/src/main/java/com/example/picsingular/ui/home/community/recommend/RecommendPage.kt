package com.example.picsingular.ui.home.community.recommend

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.picsingular.App
import com.example.picsingular.ui.components.banner.Banner
import com.example.picsingular.ui.components.items.singular.SingularItem
import com.example.picsingular.ui.components.swipe.SwipeRefreshListColumn

@Composable
fun RecommendPage(
    navHostController: NavHostController,
    viewModel: RecommendViewModel = hiltViewModel()
){
    // 获取 banner 列表
    viewModel.intentHandler(RecommendViewAction.GetBannerList)
    Column(modifier = Modifier.fillMaxSize()) {
        // banner
        val recommendPageState = viewModel.recommendPageState
        val recommendPageDataList = recommendPageState.pageDataList.collectAsLazyPagingItems()
        val listState = LazyListState()
        SwipeRefreshListColumn(
            lazyPagingItems = recommendPageDataList,
            listState = listState,
        ){
            item {
                Banner(recommendPageState.bannerList, onClickListener = { linkUrl, title ->
                    Log.e("wgw", "CommunityPage: $linkUrl $title")
                }
                )
            }
            itemsIndexed(recommendPageDataList){ _, item ->
                SingularItem(singularData = item!!, navHostController = navHostController, isSelf = item.userId == App.appState.userInfo?.userId)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }

}