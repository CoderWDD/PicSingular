package com.example.picsingular.ui.components.items.subscription

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.picsingular.bean.User
import com.example.picsingular.common.utils.images.ImageUrlUtil

@Composable
fun SubscriptionUserItem(
    userInfo: User,
    onUnSubscribeClick: () -> Unit = {},
    onItemClick: () -> Unit = {}
){
    var unSubscribe by remember { mutableStateOf(false) }
    if (unSubscribe) return
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick() }
        .padding(horizontal = 16.dp)
        .height(60.dp)
    ){
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (avatarImage,usernameText,signatureText,unSubscribeButton,divider) = createRefs()
            val avatarImageUrl = ImageUrlUtil.getAvatarUrl(userInfo.username, fileName = userInfo.avatar ?: "")
            AsyncImage(
                model = avatarImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(48.dp)
                    .constrainAs(avatarImage) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    },
                contentScale = ContentScale.FillBounds
            )
            
            Text(
                text = userInfo.username,
                fontSize = 16.sp,
                fontWeight = W600,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                modifier = Modifier.constrainAs(usernameText){
                start.linkTo(avatarImage.end, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
                }
            )
            
            Text(
                text = userInfo.signature ?: "这个人很懒，什么都没有留下",
                fontWeight = W500,
                color = Color.Gray,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(signatureText) {
                    start.linkTo(avatarImage.end, margin = 8.dp)
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                }
            )

            Button(
                onClick =
                {
                    unSubscribe = true
                    onUnSubscribeClick()
                },
                contentPadding = PaddingValues(start = 4.dp, top = 3.dp, end = 4.dp, bottom = 3.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, color = Color.Gray),
                modifier = Modifier
                    .width(72.dp)
                    .height(32.dp)
                    .constrainAs(unSubscribeButton) {
                        end.linkTo(parent.end, margin = 8.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Text(
                    text = "取消关注",
                    fontWeight = W500,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Divider(modifier = Modifier.height(1.dp).alpha(0.3f).padding(start = 56.dp).constrainAs(divider){
                bottom.linkTo(parent.bottom, margin = 4.dp)
            }, color = Color.Gray)
        }
    }
}