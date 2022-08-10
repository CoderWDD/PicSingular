package com.example.picsingular.ui.components.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.picsingular.R
import com.example.picsingular.routes.NavRoutes

@Composable
fun Drawer(pageNavHostController: NavHostController) {
    val textList = listOf("图床设置","PicSingular设置","关于PicSingular","退出")
    val iconList = listOf(
        R.drawable.bed_setting,
        R.drawable.pic_singular_setting,
        R.drawable.about,
        R.drawable.logout
    )
    ConstraintLayout {
        val (column) = createRefs()
        Column(
            Modifier
                .background(Color.White)
                .fillMaxSize()
                .constrainAs(column) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // TODO 打开登录页面
                        pageNavHostController.navigate(NavRoutes.Login.route)
                    }
                    .padding(top = 40.dp, bottom = 20.dp)
                    .clip(shape = RoundedCornerShape(8.dp))

            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(80.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column (modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(text = "请先登录", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "登录后显示")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = MaterialTheme.colors.secondary, modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 12.dp))
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
            ){
                for (i in textList.indices) {
                    DrawerItem(text = textList[i], icon = iconList[i],onClick = {

                    })
                }
            }
            Spacer(modifier = Modifier.height(120.dp))
            Divider(color = MaterialTheme.colors.secondary, modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 12.dp))
            Text(text = "更多功能待开发...", fontSize = 24.sp, modifier = Modifier.padding(start = 24.dp, top = 24.dp))
        }
    }


}

@Composable
fun DrawerItem(text: String,icon: Int, onClick: () -> Unit) {
    Box(modifier = Modifier
        .height(60.dp)
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .clickable { onClick() }){
        Row( modifier = Modifier.align(Alignment.CenterStart)) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(painter = painterResource(id = icon), contentDescription = "",Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = text)
        }
    }
}