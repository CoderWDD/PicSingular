package com.example.picsingular.ui.home.community.subscription.userhome

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.example.picsingular.bean.Singular
import com.example.picsingular.bean.User
import com.example.picsingular.common.utils.images.ImageUrlUtil
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.ui.components.items.singular.SingularItem
import com.example.picsingular.ui.components.swipe.SwipeRefreshListColumn

@Composable
fun UserHomePage(
    navHostController: NavHostController,
    userData: User?,
    viewModel: UserHomeViewModel = hiltViewModel()
){
    val userHomeViewState = viewModel.userHomeViewState
    val singularDataList = userHomeViewState.singularPageDataList.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    viewModel.intentHandler(UserHomeViewAction.GetSingularListByUserId(userId = userData?.userId ?: -1L))
    Column(modifier = Modifier.fillMaxSize()) {
        SwipeRefreshListColumn(lazyPagingItems = singularDataList, listState = listState){
            val avatarUrl = ImageUrlUtil.getAvatarUrl(username = userData?.username ?: "", fileName = userData?.avatar ?: "")
            item {
                ConstraintLayout(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)) {
                    val (backIcon, avatarImage, usernameText,signatureText) = createRefs()

                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null, modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp)
                        .size(32.dp)
                        .clickable {
                            NavHostUtil.navigateBack(navHostController = navHostController)
                        }
                        .constrainAs(backIcon) {
//                            start.linkTo(parent.start)
//                            top.linkTo(parent.top)
                        })

                    AsyncImage(model = avatarUrl, contentDescription = null, contentScale = ContentScale.Crop , modifier = Modifier
                        .size(120.dp)
                        .clip(shape = CircleShape)
                        .constrainAs(avatarImage) {
                            top.linkTo(parent.top, margin = 24.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })

                    Text(
                        text = userData?.username ?: "加载中...",
                        fontSize = 24.sp,
                        fontWeight = W600,
                        color = Color.Black,
                        modifier = Modifier.constrainAs(usernameText) {
                            top.linkTo(avatarImage.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })

                    Text(
                        text = userData?.signature ?: "这个人很懒，什么都没留下",
                        fontSize = 18.sp,
                        fontWeight = W500,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(260.dp).constrainAs(signatureText) {
                            top.linkTo(usernameText.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })
                }
            }

            itemsIndexed(singularDataList){_: Int, item: Singular? ->
                SingularItem(singularData = item!!, navHostController = navHostController)
            }
        }
    }
}