package com.example.picsingular.ui.home.community

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.picsingular.ui.components.banner.Banner
import com.example.picsingular.ui.components.banner.BannerData
import com.example.picsingular.ui.components.topbar.CommunityTabBar
import com.example.picsingular.ui.home.community.collect.CollectPage
import com.example.picsingular.ui.home.community.recommend.RecommendPage
import com.example.picsingular.ui.home.community.saved.SavedPage
import com.example.picsingular.ui.home.community.shared.SharedPage
import com.example.picsingular.ui.home.community.subscription.SubscriptionPage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

data class Page(val title: String, val page: @Composable (NavHostController) -> Unit)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CommunityPage(
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: CommunityViewModel = hiltViewModel()
) {
    val pagerStatus = rememberPagerState()
    val pagesList = listOf(
        Page("推荐"){ RecommendPage(navHostController = navHostController)},
        Page("订阅"){ SubscriptionPage(navHostController = navHostController)},
        Page("收藏"){ CollectPage(navHostController = navHostController) },
        Page("分享"){ SharedPage(navHostController = navHostController) },
        Page("保存"){ SavedPage(navHostController = navHostController) },
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val coroutineScope = rememberCoroutineScope()
        CommunityTabBar(pagesList = pagesList, currentTab = pagerStatus.currentPage, scaffoldState = scaffoldState) { index ->
            coroutineScope.launch {
                pagerStatus.scrollToPage(index)
            }
        }

        HorizontalPager(
            count = pagesList.size,
            state = pagerStatus
        ) {pageIndex ->
            when (pageIndex){
                0 -> pagesList[0].page(navHostController)
                1 -> pagesList[1].page(navHostController)
                2 -> pagesList[2].page(navHostController)
                3 -> pagesList[3].page(navHostController)
                4 -> pagesList[4].page(navHostController)
            }
        }
    }

}
