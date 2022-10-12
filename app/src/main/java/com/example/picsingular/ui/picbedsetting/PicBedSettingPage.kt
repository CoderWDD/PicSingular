package com.example.picsingular.ui.picbedsetting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.ui.theme.Grey200

@Composable
fun PicBedSettingPage(navHostController: NavHostController){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        // title
        Text(text = "图床设置", fontSize = 24.sp, fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Grey200)
        )
//        val
//        TextField(value = , onValueChange = )

    }

    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier
        .padding(start = 16.dp)
        .size(32.dp)
        .clickable {
            NavHostUtil.navigateBack(navHostController)
        })
}