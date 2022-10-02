package com.example.picsingular.ui.home.release

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.picsingular.R
import com.example.picsingular.ui.theme.BorderColor
import com.example.picsingular.ui.theme.ButtonBackground
import com.example.picsingular.ui.theme.FocusedIndicatorColor
import com.example.picsingular.ui.theme.Grey400
import com.example.picsingular.ui.theme.TransparentColor
import com.example.picsingular.ui.theme.UnfocusedIndicatorColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReleasePage(navHostController: NavHostController, viewModel: ReleaseViewModel = hiltViewModel()) {
    val imageUrlList = mutableListOf<String>()
    val title = remember { mutableStateOf("") }
    val contentDescription = remember { mutableStateOf("") }
    val releaseState = viewModel.releasePageState
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // title
        Text(text = "发布分享", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // 顶部本地图片预览
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(color = Color.White)
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            imageUrlList.forEach { imageUrl ->
                AddImageButton(imagePath = imageUrl)
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BorderColor)
                .height(2.dp)
        )

        // 标题输入框
        TextField(
            value = title.value,
            onValueChange = {
                title.value = it
                Log.e("wgw", "ReleasePage: $title")
            },
            placeholder = {
                Text(
                    text = "填写标题会有更多赞哦~",
                    color = MaterialTheme.colors.secondary
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primary,
                textColor = MaterialTheme.colors.onPrimary,
                focusedIndicatorColor = FocusedIndicatorColor,
                unfocusedIndicatorColor = UnfocusedIndicatorColor,
                cursorColor = MaterialTheme.colors.onPrimary
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions { focusManager.clearFocus() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 描述输入框
        TextField(
            value = contentDescription.value,
            onValueChange = { contentDescription.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .border(
                    width = 1.dp,
                    color = FocusedIndicatorColor,
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primary,
                textColor = MaterialTheme.colors.onPrimary,
                unfocusedIndicatorColor = TransparentColor,
                focusedIndicatorColor = TransparentColor,
                cursorColor = MaterialTheme.colors.onPrimary
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions { focusManager.clearFocus() },
            placeholder = {
                Text(text = "给你的分享配上文案吧~", color = MaterialTheme.colors.secondary)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 保存或发布按钮
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(54.dp)
        ) {
            OutlinedButton(
                onClick = {
                    val singularInfo = SingularInfo(content = contentDescription.value,title = title.value, imageUrlList = imageUrlList)
                    viewModel.intentHandler(ReleasePageAction.SaveSingular(singularInfo = singularInfo))
                },
                modifier = Modifier.weight(1f, fill = true),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = TransparentColor
                ),
                border = BorderStroke(width = 1.dp, color = Grey400)
            ) {
                Text(text = "保存")
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(
                onClick = {
                    val singularInfo = SingularInfo(content = contentDescription.value,title = title.value, imageUrlList = imageUrlList)
                    viewModel.intentHandler(ReleasePageAction.ReleaseSingular(singularInfo = singularInfo))
                },
                modifier = Modifier.weight(3f, fill = true),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonBackground,
                    contentColor = Color.White
                )
            ) {
                Text(text = "发布")
            }
        }
    }
}

@Composable
fun AddImageButton(imagePath: String? = null, isLast: Boolean = false) {
    ConstraintLayout(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = BorderColor,
                shape = RoundedCornerShape(24.dp)
            )
            .size(100.dp)
            .clickable { }
    ) {
        val (addIcon, image) = createRefs()
        if (!isLast) {
            Icon(painter = painterResource(id = R.drawable.add),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .alpha(0.5f)
                    .constrainAs(addIcon) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })
        } else {
            AsyncImage(model = imagePath,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })
        }
    }
}