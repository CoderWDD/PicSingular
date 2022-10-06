package com.example.picsingular.ui.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.picsingular.App
import com.example.picsingular.AppAction
import com.example.picsingular.R
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.ui.components.snackbar.SnackBarInfo
import com.example.picsingular.ui.theme.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPage(
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val loginState = viewModel.viewState
    // 如果登录成功，就返回
    if (loginState.isLogin) {
        viewModel.intentHandler(LoginViewAction.NavBack(navHostController = navHostController))
    }

    val snackBarHostState = remember{ SnackbarHostState() }

    LaunchedEffect(Unit){
        viewModel.viewEvent.collect{event ->
            when (event){
                is LoginEvent.MessageEvent -> snackBarHostState.showSnackbar(message = event.msg)
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        keyboardController?.hide()
                    }
                )
            },
    ) {
        Text(text = "Hello Again!", color = MaterialTheme.colors.onPrimary, fontSize = H2, fontWeight = FontWeight.Bold)
        Text(text = "welcome back, you've been missed!", color = MaterialTheme.colors.secondaryVariant, fontSize = H4)
        Spacer(modifier = Modifier.height((24.dp)))
        TextField(
            value = username,
            singleLine = true,
            onValueChange = { username = it },
            label = { Text(text = "Username", color = MaterialTheme.colors.secondary, fontSize = H4) },
            leadingIcon = { Icon(Icons.Filled.AccountCircle,null) },
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .clip(shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                focusedIndicatorColor = MaterialTheme.colors.secondary.copy(alpha = ContentAlpha.high),
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            singleLine = true,
            onValueChange = { password = it },
            label = { Text(text = "Password", color = MaterialTheme.colors.secondary, fontSize = H4) },
            leadingIcon = { Icon(Icons.Filled.Lock,null) },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }, modifier = Modifier.size(24.dp)) {
                    if (showPassword) {
                        Icon(painter = painterResource(id = R.drawable.visibility),null)
                    } else {
                        Icon(painter = painterResource(id = R.drawable.visibility_off),null)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .clip(shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                focusedIndicatorColor = MaterialTheme.colors.secondary.copy(alpha = ContentAlpha.high),
            ),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.intentHandler(LoginViewAction.Login(username = username, password = password))
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            keyboardController?.hide()
            viewModel.intentHandler(LoginViewAction.Login(username = username, password = password))
        }, modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color(0x5fbdbdbd), shape = RoundedCornerShape(8.dp))) {
            Text(text = "Login", color = MaterialTheme.colors.onPrimary, fontSize = H4)
        }
        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Forgot Password?", color = MaterialTheme.colors.secondary, fontSize = H4, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Divider(color = MaterialTheme.colors.secondary, modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .height(1.dp))
            Text(text = "OR", color = MaterialTheme.colors.secondaryVariant, fontSize = H4, fontWeight = FontWeight.Bold)
            Divider(color = MaterialTheme.colors.secondary, modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .height(1.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /*TODO*/ }, modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color(0x5fbdbdbd), shape = RoundedCornerShape(8.dp))) {
            Text(text = "Register", color = MaterialTheme.colors.onPrimary, fontSize = H4)
        }
    }

    // 显示event信息
    SnackBarInfo(snackBarHostState = snackBarHostState)

    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier
        .padding(start = 16.dp)
        .size(32.dp)
        .clickable {
            NavHostUtil.navigateBack(navHostController)
        })
}
