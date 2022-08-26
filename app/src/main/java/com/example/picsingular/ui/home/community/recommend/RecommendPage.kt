package com.example.picsingular.ui.home.community.recommend

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.picsingular.ui.components.banner.Banner
import com.example.picsingular.ui.components.banner.BannerData
import com.example.picsingular.ui.components.items.singular.SingularItem
import com.example.picsingular.ui.components.swipe.SwipeRefreshList

@Composable
fun RecommendPage(
    navHostController: NavHostController,
    viewModel: RecommendViewModel = hiltViewModel()
){
    Column(modifier = Modifier.fillMaxSize()) {
        // banner
        // TODO : add banner data from server

        val recommendPageState = remember { viewModel.recommendPageState }
        val recommendPageDataList = recommendPageState.pageDataList.collectAsLazyPagingItems()
        val listState = LazyListState()
        SwipeRefreshList(
            lazyPagingItems = recommendPageDataList,
            listState = listState,
        ){
            item {
                val listBanner = mutableListOf<BannerData>()
                repeat(5) {
                    listBanner.add(
                        BannerData(
                            title = "Title $it",
                            imageUrl = "https://api.btstu.cn/sjbz/api.php",
                            linkUrl = ""
                        )
                    )
                }
                Banner(listBanner, onClickListener = { linkUrl, title ->
                    Log.e("wgw", "CommunityPage: $linkUrl $title")
                }
                )
            }
            itemsIndexed(recommendPageDataList){ index, item ->
                SingularItem(singularData = item!!, navHostController = navHostController)
            }
        }
    }

}