package com.example.picsingular.ui.details

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.picsingular.R
import com.example.picsingular.bean.Singular
import com.example.picsingular.common.utils.images.ImageUrlUtil
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.ui.components.items.comment.CommentItem
import com.example.picsingular.ui.components.items.singular.SingularUserInfoAction
import com.example.picsingular.ui.components.swipe.SwipeRefreshList
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SingularDetailPage(
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    singularData: Singular?,
    viewModel: SingularDetailsViewModel = hiltViewModel()
){
    viewModel.intentHandler(SingularDetailsAction.GetUserInfo(singularData?.userId!!))
    var hasThumbUp by remember { mutableStateOf(false) }
    var hasFavorite by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val singularDetailState = viewModel.singularDetailsState
    val commentDataListPaging = singularDetailState.commentDataList?.collectAsLazyPagingItems()
    val userInfo = singularDetailState.userInfo

    // 获取评论列表
    viewModel.intentHandler(SingularDetailsAction.GetCommentList(singularData.singularId))
    Column(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    keyboardController?.hide()
                }
            )
        },
    ) {
        val listState = LazyListState()
        SwipeRefreshList(lazyPagingItems = commentDataListPaging!!, listState = listState){
            // 用户信息 topBar
            item {
                Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)) {
                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val (backIcon,avatarIcon,usernameText,subscribeButton) = createRefs()
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null, modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp)
                            .size(32.dp)
                            .clickable {
                                NavHostUtil.navigateBack(navHostController = navHostController)
                            }
                            .constrainAs(backIcon) {})

                        AsyncImage(
                            model = ImageUrlUtil.getAvatarUrl(username = userInfo?.username ?: "loading..."),
                            contentDescription = null,
                            placeholder = painterResource(id = R.drawable.avatar),
                            error = painterResource(id = R.drawable.avatar),
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = CircleShape)
                                .constrainAs(avatarIcon) {
                                    top.linkTo(backIcon.top, margin = 6.dp)
                                    start.linkTo(backIcon.end, margin = 8.dp)
                                    bottom.linkTo(backIcon.bottom)
                                }
                        )

                        Text(text = userInfo?.username ?: "loading...", fontSize = 18.sp, fontWeight = FontWeight.W500, modifier = Modifier.constrainAs(usernameText){
                            start.linkTo(avatarIcon.end, margin = 8.dp)
                            top.linkTo(backIcon.top, margin = 8.dp)
                            bottom.linkTo(backIcon.bottom)
                        })

                        Button(
                            contentPadding = PaddingValues(start = 4.dp, top = 3.dp, end = 4.dp, bottom = 3.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, if (singularDetailState.hasSubscribe) Color.Gray else Color.Red),
                            modifier = Modifier
                                .size(width = 56.dp, height = 24.dp)
                                .constrainAs(subscribeButton) {
                                    end.linkTo(parent.end, margin = 16.dp)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                },
                            onClick = {
                                Log.e("wgw", "SingularDetailPage: ${singularDetailState.hasSubscribe}", )
                                viewModel.intentHandler( if (singularDetailState.hasSubscribe) SingularDetailsAction.UnSubscribeUser(singularData.userId) else SingularDetailsAction.SubscribeUser(singularData.userId))
                            }
                        ) {
                            Text(
                                text = if (singularDetailState.hasSubscribe) "不关注" else "关注",
                                fontWeight = W500,
                                fontSize = 14.sp,
                                color = if (singularDetailState.hasSubscribe) Color.Gray else Color.Red
                            )
                        }
                    }
                }
                Divider(color = Color.Gray, modifier = Modifier.alpha(0.3f))
            }

            // 图片列表
            item {
                val pagerSize = singularData.imageList?.size ?: 0
                val pagerState = rememberPagerState(0)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)) {
                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val (imageContainer,indicatorContainer) = createRefs()
                        HorizontalPager(
                            count = pagerSize,
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .constrainAs(imageContainer) {}
                        ) {page ->
                            SubcomposeAsyncImage(
                                model = ImageUrlUtil.getImageUrl(singularData.imageList?.get(page)?.imageUrl ?: "", singularId = singularData.singularId),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            ){
                                when(painter.state){
                                    is AsyncImagePainter.State.Loading -> {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.BottomCenter)
                                        )
                                    }
                                    is AsyncImagePainter.State.Error -> {
                                        Image(
                                            painter = painterResource(id = R.drawable.banner_placeholder),
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop,
                                            contentDescription = "Singular Image",
                                        )
                                    }
                                    else -> {
                                        SubcomposeAsyncImageContent()
                                    }
                                }
                            }
                        }
                        Box(modifier = Modifier
                            .constrainAs(indicatorContainer){
                                bottom.linkTo(imageContainer.bottom, margin = 8.dp)
                                start.linkTo(imageContainer.start)
                                end.linkTo(imageContainer.end)
                            }
                        ){
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                for(i in 0 until pagerSize){
                                    var indicatorSize by remember { mutableStateOf(4.dp) }
                                    indicatorSize = if (i == pagerState.currentPage) 8.dp else 4.dp
                                    val indicatorColor = if (i == pagerState.currentPage) Color.Gray else Color.White
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(indicatorColor)
                                            .animateContentSize()
                                            .size(indicatorSize)
                                    )
                                    if (i != pagerSize - 1) Spacer(modifier = Modifier.size(4.dp))
                                }
                            }
                        }
                    }
                }
            }

            // 内容描述
            item {
                Text(text = singularData.description, fontWeight = W500, fontSize = 24.sp , modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp))

                Spacer(modifier = Modifier.height(8.dp))
                ConstraintLayout(modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)) {
                    val (pushDate, likeIcon, favoriteIcon) = createRefs()
                    Text(text = singularData.pushDate, color = Color.Gray ,fontSize = 14.sp, fontWeight = FontWeight.W500, modifier = Modifier.constrainAs(pushDate){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 16.dp)
                    })

                    Icon(imageVector = if (hasThumbUp) Icons.Default.ThumbUp else Icons.Outlined.ThumbUp, contentDescription = null, modifier = Modifier
                        .padding(start = 16.dp)
                        .size(24.dp)
                        .clickable {
                            hasThumbUp = if (hasThumbUp) {
                                viewModel.intentHandler(
                                    SingularDetailsAction.AddLikeCount(
                                        singularData.singularId
                                    )
                                )
                                false
                            } else {
                                viewModel.intentHandler(
                                    SingularDetailsAction.SubLikeCount(
                                        singularData.singularId
                                    )
                                )
                                true
                            }
                        }
                        .constrainAs(likeIcon) {
                            top.linkTo(parent.top)
                            end.linkTo(favoriteIcon.start)
                        }
                    )

                    Icon(imageVector = if (hasFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder, contentDescription = null, modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                        .clickable {
                            hasFavorite = if (hasFavorite) {
                                viewModel.intentHandler(
                                    SingularDetailsAction.UnFavoriteSingular(
                                        singularData.singularId
                                    )
                                )
                                false
                            } else {
                                viewModel.intentHandler(
                                    SingularDetailsAction.FavoriteSingular(
                                        singularData.singularId
                                    )
                                )
                                true
                            }
                        }
                        .constrainAs(favoriteIcon) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                    )
                }

                Divider(color = Color.Gray, modifier = Modifier
                    .alpha(0.3f)
                    .padding(horizontal = 16.dp, vertical = 8.dp))

            }

            item {
                var commentValue by remember { mutableStateOf("") }
                // 内容评论
                TextField(
                    placeholder = { Text(text = "留下你的评论吧~", color = Color.Black, fontWeight = FontWeight.W500, fontSize = 14.sp, textAlign = TextAlign.Center) },
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    leadingIcon = { Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)},
                    shape = RoundedCornerShape(16.dp),
                    value = commentValue,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = MaterialTheme.colors.secondary.copy(alpha = ContentAlpha.high),
                        unfocusedIndicatorColor = MaterialTheme.colors.background
                    ),
                    onValueChange = {commentValue = it},
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            // 发送评论
                            viewModel.intentHandler(SingularDetailsAction.SendComment(singularData.singularId, commentValue))
                            commentValue = ""
                        }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            if (commentDataListPaging.itemCount == 0){
                item {Text(text = "暂无评论~", fontWeight = FontWeight.W500, fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }
            }else{
                itemsIndexed(commentDataListPaging){index, item ->
                    if (index != 0){
                        Divider(modifier = Modifier.height(1.dp).alpha(0.3f).padding(start = 56.dp), color = Color.Gray)
                    }
                    CommentItem(comment = item!!)
                }
            }
        }
    }
}



