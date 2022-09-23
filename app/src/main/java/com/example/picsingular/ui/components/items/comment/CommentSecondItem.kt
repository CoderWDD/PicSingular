package com.example.picsingular.ui.components.items.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.picsingular.R
import com.example.picsingular.bean.CommentLevelSecond
import com.example.picsingular.common.utils.images.ImageUrlUtil

@Composable
fun CommentSecondItem(comment: CommentLevelSecond, viewModel: CommentSecondItemViewModel = hiltViewModel()){
    viewModel.intentHandler(CommentSecondItemAction.GetUserInfo(comment.userId))
    val commentSecondItemViewState = viewModel.commentSecondItemState
    val userInfo = commentSecondItemViewState.userInfo
    val avatarUrl = ImageUrlUtil.getAvatarUrl( username = userInfo?.username ?: "", fileName = userInfo?.avatar ?: "")
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .clickable {  }
        .background(color = Color.Green)){
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (avatarImage, usernameText, commentText) = createRefs()
            AsyncImage(
                model = avatarUrl,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.avatar),
                error = painterResource(id = R.drawable.avatar),
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
    }
}