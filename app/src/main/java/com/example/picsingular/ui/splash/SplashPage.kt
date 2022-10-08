package com.example.picsingular.ui.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.picsingular.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashPage(invokeFunction: () -> Unit) {
    val scope = rememberCoroutineScope()
    scope.launch {
        delay(3000L)
        invokeFunction.invoke()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.CenterHorizontally)) {

            val (title, background, description, skipButton, skipIcon) = createRefs()
            Text(text = "PicSingular", fontWeight = FontWeight.Bold, fontSize = 32.sp, modifier = Modifier.constrainAs(title){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            })

            Image(painter = painterResource(id = R.drawable.splash_background), contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .constrainAs(background) {
                    top.linkTo(title.bottom, margin = 24.dp)
                })

            Text(text = "PicSingular is an platform where you can discover amazing images and share your daily thought.",textAlign = TextAlign.Center, fontSize = 16.sp, fontWeight = FontWeight.W500, modifier = Modifier.constrainAs(description){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(title.bottom, margin = 8.dp)
            })

            TextButton(onClick = { invokeFunction.invoke() }, modifier = Modifier.constrainAs(skipButton){
                end.linkTo(parent.end, margin = 16.dp)
                top.linkTo(background.bottom)
            }) {
                Text(text = "skip", color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.W500)
            }

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null, modifier = Modifier
                .clickable { invokeFunction.invoke() }
                .constrainAs(skipIcon) {
                    start.linkTo(skipButton.end)
                    top.linkTo(skipButton.top)
                    bottom.linkTo(skipButton.bottom)
                })
        }

    }
}


