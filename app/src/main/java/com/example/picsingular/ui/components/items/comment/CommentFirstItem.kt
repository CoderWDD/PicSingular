package com.example.picsingular.ui.components.items.comment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.picsingular.R
import com.example.picsingular.bean.CommentLevelFirst
import com.example.picsingular.common.utils.images.ImageUrlUtil

@Composable
fun CommentItem(
    comment: CommentLevelFirst,
    viewModel: CommentFirstItemViewModel = hiltViewModel()
) {
    val commentFirstItemState = viewModel.commentFirstItemState
    val userInfo = commentFirstItemState.userInfo

    val avatarUrl = ImageUrlUtil.getAvatarUrl(username = comment.username, fileName = comment.avatar)
    Log.e("wgw", "CommentItem: $avatarUrl", )
    Log.e("wgw", "CommentItem: ${comment.username}")

    // 根据userId获取用户信息
//    viewModel.intentHandler(CommentFirstItemAction.GetUserInfo(comment.userId))

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)){
        ConstraintLayout {
            val (avatarImage, usernameText, commentText, secondCommentBox) = createRefs()

            ConstraintLayout(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // TODO 弹出回复框
                }) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.avatar),
                    error = painterResource(id = R.drawable.avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape)
                        .constrainAs(avatarImage) {
                            top.linkTo(parent.top, margin = 8.dp)
                        }
                )

                Text(
                    text = userInfo?.username ?: "加载中",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.constrainAs(usernameText) {
                        top.linkTo(parent.top)
                        start.linkTo(avatarImage.end, margin = 8.dp)
                    }
                )

                Text(
                    text = comment.content,
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.constrainAs(commentText) {
                        top.linkTo(usernameText.bottom)
                        start.linkTo(avatarImage.end, margin = 8.dp)
                    }
                )
            }

            Box (modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Red)
                .constrainAs(secondCommentBox) {
                    start.linkTo(avatarImage.end, margin = 8.dp)
                }){
                comment.commentSecondList.forEach {
                    CommentSecondItem(comment = it)
                }
            }
        }
    }
}