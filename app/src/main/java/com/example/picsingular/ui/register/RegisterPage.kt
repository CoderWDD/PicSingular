package com.example.picsingular.ui.register

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NavUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.picsingular.R
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.ui.components.snackbar.SnackBarInfo
import com.example.picsingular.ui.login.LoginViewAction
import com.example.picsingular.ui.theme.H4
import com.example.picsingular.ui.theme.White
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterPage(
    navHostController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val snackBarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()
    scope.launch {
        viewModel.viewEvent.collect { event ->
            when (event) {
                is RegisterEvent.NavBack -> NavHostUtil.navigateBack(navHostController = navHostController)
                is RegisterEvent.MessageEvent -> snackBarHostState.showSnackbar(message = event.msg)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Discover amazing pictures,\n share your images here",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.register_page_header),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))



        TextField(
            value = username,
            singleLine = true,
            onValueChange = { username = it },
            label = {
                Text(
                    text = "Username",
                    color = MaterialTheme.colors.secondary,
                    fontSize = H4
                )
            },
            leadingIcon = { Icon(Icons.Filled.AccountCircle, null) },
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
            label = {
                Text(
                    text = "Password",
                    color = MaterialTheme.colors.secondary,
                    fontSize = H4
                )
            },
            leadingIcon = { Icon(Icons.Filled.Lock, null) },
            trailingIcon = {
                IconButton(
                    onClick = { showPassword = !showPassword },
                    modifier = Modifier.size(24.dp)
                ) {
                    if (showPassword) {
                        Icon(painter = painterResource(id = R.drawable.visibility), null)
                    } else {
                        Icon(painter = painterResource(id = R.drawable.visibility_off), null)
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
                    viewModel.intentHandler(RegisterAction.Register(username, password))
                    keyboardController?.hide()
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                keyboardController?.hide()
                viewModel.intentHandler(RegisterAction.Register(username, password))
            }, modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color(0x5fbdbdbd), shape = RoundedCornerShape(8.dp))
        ) {
            Text(text = "Register", color = MaterialTheme.colors.onPrimary, fontSize = H4)
        }
    }

    SnackBarInfo(snackBarHostState = snackBarHostState)

    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier
        .padding(start = 16.dp)
        .size(32.dp)
        .clickable {
            NavHostUtil.navigateBack(navHostController)
        })
}