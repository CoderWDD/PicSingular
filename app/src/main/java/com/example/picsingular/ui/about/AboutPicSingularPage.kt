package com.example.picsingular.ui.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.ui.theme.H2
import com.example.picsingular.ui.theme.H3
import com.example.picsingular.ui.theme.H4

@Composable
fun AboutPicSingularPage(navHostController: NavHostController){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "About me", color= Color.Black, fontSize = H2, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "    This is a picture share platform made by Compose, Use Kotlin as the main develop language instead of java.", fontSize = H4)
        Text(text = "    Integrated common picture share functions and pictureBed module. You can share your favourite images with other users and exhibit your personal image collection in your PicBed.", fontSize = H4)
        Text(text = "github link")
        Text(text = "This Project is code & design with love", fontSize = H4)
        Text(text = "by CoderWdd & Lorde", fontSize = H4)
    }
    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier
        .padding(start = 16.dp)
        .size(32.dp)
        .clickable {
            NavHostUtil.navigateBack(navHostController)
        })
}