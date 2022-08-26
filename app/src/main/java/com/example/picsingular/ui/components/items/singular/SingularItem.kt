package com.example.picsingular.ui.components.items.singular

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.picsingular.R
import com.example.picsingular.bean.ImageUrl
import com.example.picsingular.bean.Singular
import com.example.picsingular.common.utils.images.ImageUrlUtil
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.routes.NavRoutes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingularItem(
    singularData: Singular,
    navHostController: NavHostController,
    hasThumbUp: Boolean = false,
    hasFavorite: Boolean = false,
    isSelf: Boolean = false,
    onLongClick: () -> Unit = {},
    viewModel: SingularItemViewModel = hiltViewModel()
){
    viewModel.singularUserInfoActionHandler(SingularUserInfoAction.GetUserInfo(singularData.userId))
    val userInfoState = remember{ viewModel.userInfoState }
    val hasThumbUp = remember { mutableStateOf(hasThumbUp) }
    val hasFavorite = remember { mutableStateOf(hasFavorite) }
    Card (
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .padding(start = 8.dp, end = 8.dp)
            .combinedClickable(
                onClick = {
                    NavHostUtil.navigateTo(
                        navHostController = navHostController,
                        destinationRouteName = NavRoutes.SingularDetailPage.route,
                        args = singularData
                    )
                },
                onLongClick = {onLongClick()}
            )

    ) {
        ConstraintLayout (modifier = Modifier.fillMaxSize()){
            val (avatar,usernameText,moreIcon,singularImagesContainer,favoriteIcon,thumbUpIcon,singularDescriptionText,pushDataText) = createRefs()
            val avatarUrl = ImageUrlUtil.getAvatarUrl(userInfoState.userInfo?.username ?: "")
            AsyncImage(
                model = avatarUrl,
                contentDescription = "avatar of singular",
                placeholder = painterResource(id = R.drawable.avatar),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.avatar),
                modifier = Modifier
                    .size(32.dp)
                    .clip(shape = CircleShape)
                    .constrainAs(avatar) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                    },
            )
            Text(text = userInfoState.userInfo?.username ?: "username", fontSize = 18.sp, fontWeight = FontWeight.W500, modifier = Modifier.constrainAs(usernameText){
                top.linkTo(avatar.top)
                bottom.linkTo(avatar.bottom)
                start.linkTo(avatar.end, margin = 8.dp)
            })

            Text(text = singularData.pushDate, fontSize = 12.sp, fontWeight = FontWeight.W400, modifier = Modifier.constrainAs(pushDataText){
                top.linkTo(avatar.top)
                bottom.linkTo(avatar.bottom)
                end.linkTo(parent.end, margin = 8.dp)
            })

            if (!isSelf){
                Icon(imageVector = if (hasFavorite.value) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder, contentDescription = null, modifier = Modifier
                    .clickable {
                        if (hasFavorite.value) {
                            viewModel.singularUserInfoActionHandler(
                                SingularUserInfoAction.UnFavoriteSingular(
                                    singularData.singularId
                                )
                            )
                            hasFavorite.value = false
                        } else {
                            viewModel.singularUserInfoActionHandler(
                                SingularUserInfoAction.FavoriteSingular(
                                    singularData.singularId
                                )
                            )
                            hasFavorite.value = true
                        }
                    }
                    .constrainAs(favoriteIcon) {
                        end.linkTo(parent.end, margin = 8.dp)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    })
            }

            if (!isSelf){
                Icon(imageVector = if (hasThumbUp.value) Icons.Default.ThumbUp else Icons.Outlined.ThumbUp, contentDescription = null, modifier = Modifier
                    .clickable {
                        if (hasThumbUp.value) {
                            viewModel.singularUserInfoActionHandler(
                                SingularUserInfoAction.AddLikeCount(
                                    singularData.singularId
                                )
                            )
                            hasThumbUp.value = false
                        } else {
                            viewModel.singularUserInfoActionHandler(
                                SingularUserInfoAction.SubLikeCount(
                                    singularData.singularId
                                )
                            )
                            hasThumbUp.value = true
                        }
                    }
                    .constrainAs(thumbUpIcon) {
                        end.linkTo(favoriteIcon.start, margin = 8.dp)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    })
            }

            Text(
                text = singularData.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .width(280.dp)
                    .constrainAs(singularDescriptionText) {
                        start.linkTo(parent.start, margin = 8.dp)
                        bottom.linkTo(parent.bottom, margin = 12.dp)
                    })

            SingularImagesContainer(singularId = singularData.singularId,imageList = singularData.imageList!!, modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
                .background(Color.Gray)
                .constrainAs(singularImagesContainer) {
                    top.linkTo(avatar.bottom, margin = 8.dp)
                })
        }
    }
}

@Composable
fun SingularImagesContainer(singularId: Long,imageList: List<ImageUrl>,modifier: Modifier){
    Box(modifier = modifier){
        val baseImageUrl = ImageUrlUtil.getImageUrl(imageUrl = "", singularId = singularId)
        if (imageList.size <= 2){
            AsyncImage(model = baseImageUrl + imageList[0].imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        }else if (imageList.size == 3){
            Row(modifier = Modifier.fillMaxSize()) {
                AsyncImage(model = baseImageUrl + imageList[0].imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight())
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()) {
                    AsyncImage(model = baseImageUrl + imageList[1].imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth())
                    AsyncImage(model = baseImageUrl + imageList[2].imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth())
                }
            }
        }else if (imageList.size in 4..6){
            Row(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()) {
                    AsyncImage(model = baseImageUrl + imageList[0].imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth())
                    AsyncImage(model = baseImageUrl + imageList[1].imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth())
                }
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()) {
                    AsyncImage(model = baseImageUrl + imageList[2].imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth())
                    AsyncImage(model = baseImageUrl + imageList[3].imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth())
                }
            }
        }else {
            val gridState = rememberLazyGridState()
            LazyVerticalGrid(columns = GridCells.Fixed(3), state = gridState, userScrollEnabled = false,modifier = Modifier.fillMaxSize()){
                itemsIndexed(imageList){index: Int, item: ImageUrl ->
                    if (index <= 8 ) {
                        AsyncImage(model = baseImageUrl + item.imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp))
                    }
                }
            }
        }
    }
}