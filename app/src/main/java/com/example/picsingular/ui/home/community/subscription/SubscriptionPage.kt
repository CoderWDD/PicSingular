package com.example.picsingular.ui.home.community.subscription

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.picsingular.bean.User
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.routes.NavRoutes
import com.example.picsingular.ui.components.items.subscription.SubscriptionUserItem
import com.example.picsingular.ui.components.swipe.SwipeRefreshListColumn

@Composable
fun SubscriptionPage(
    navHostController: NavHostController,
    viewModel: SubscriptionViewModel = hiltViewModel()
){
    val subscriptionViewState = viewModel.subscriptionViewState
    val userDataList = subscriptionViewState.pageDataList.collectAsLazyPagingItems()
    val listState = LazyListState()

    SwipeRefreshListColumn(
        lazyPagingItems = userDataList,
        listState = listState
    ){
        itemsIndexed(userDataList){_: Int, item: User? ->
            SubscriptionUserItem(
                userInfo = item!!,
                onUnSubscribeClick = {viewModel.intentHandler(SubscriptionViewAction.UnSubscribeUser(item.userId))},
                onItemClick = {
                    NavHostUtil.navigateTo(navHostController = navHostController, destinationRouteName = NavRoutes.UserHomePage.route, args = item)
                }
            )
        }
    }
}