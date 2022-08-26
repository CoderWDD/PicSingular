package com.example.picsingular.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.picsingular.App
import com.example.picsingular.bean.User
import com.example.picsingular.common.constants.HttpConstants
import com.example.picsingular.common.constants.TokenConstants
import com.example.picsingular.common.utils.navhost.NavHostUtil
import com.example.picsingular.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    var viewState by mutableStateOf(LoginViewState())
        private set
    fun intentHandler(action: LoginViewAction){
        when (action){
            is LoginViewAction.Login -> login(username = action.username, password = action.password)
            is LoginViewAction.SaveLoginUser -> saveLoginUser(username = action.username, password = action.password)
            is LoginViewAction.Logout -> logout()
            is LoginViewAction.UploadAvatar -> uploadAvatar(avatarPath = action.avatarPath)
            is LoginViewAction.GetUserInfo -> getUserInfo()
            is LoginViewAction.NavBack -> navBack(action.navHostController)
        }
    }

    private fun navBack(navHostController: NavHostController){
        if (viewState.navBack){
            NavHostUtil.navigateBack(navHostController)
            viewState = viewState.copy(navBack = false)
        }
    }

    private fun uploadAvatar(avatarPath: String){
        viewModelScope.launch {
            val file = File(avatarPath)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val avatarFile = MultipartBody.Part.createFormData("avatar", filename = file.name, requestFile)
            userRepository.uploadUserAvatar(avatar = avatarFile).collect{res ->
                Log.e("wgw", "uploadAvatar: $res", )
                if (res.status == HttpConstants.SUCCESS){
                    userRepository.saveUserToLocalStore(res.data)
                    App.globalUserInfo = res.data
                    viewState = viewState.copy(isSuccess = true, user = res.data)
                }else {
                    viewState = viewState.copy(isError = true, errorMessage = res.message)
                }
            }
        }
    }
    private fun logout(){
        userRepository.logout()
        TokenConstants.TOKEN = ""
        viewModelScope.launch { userRepository.removeTokenFromLocalStore() }
        App.globalUserInfo = null
        viewState = viewState.copy(isLogin = false, user = null)
    }

    private fun login(username: String, password: String) {
        viewModelScope.launch {
            userRepository.login(username = username, password = password).collect{res ->
                if (res.status == HttpConstants.SUCCESS){
                    // if login success, then save the user to local storage
                    userRepository.saveUserToLocalStore(res.data)
                    // if login success, then save the token to shared preferences
                    userRepository.saveUserTokenToLocalStore(TokenConstants.TOKEN)
                    // save user to global variable
                    App.globalUserInfo = res.data
                    // update the login status
                    viewState = viewState.copy(isSuccess = true, isError = false, user = res.data, isLogin = true, navBack = true)
                }else {
                    viewState = viewState.copy(isSuccess = false, isError = true, errorMessage = res.message, isLogin = false)
                }
            }
        }
    }

    private fun getUserInfo(){
        viewModelScope.launch {
            userRepository.getUserInfo().collect{res ->
                if (res.status == HttpConstants.SUCCESS){
                    // 将用户信息更新到本地
                    userRepository.saveUserToLocalStore(res.data)
                    App.globalUserInfo = res.data
                    Log.e("wgw", "getUserInfo: $viewState", )
                    viewState = viewState.copy(isSuccess = true, isError = false, user = res.data)
                }else {
                    Log.e("wgw", "getUserInfo fail: $viewState", )
                    viewState = viewState.copy(isError = true, isSuccess = true, errorMessage = res.message)
                }
            }
        }
    }

    private fun saveLoginUser(username: String,password: String){
        // save login user info in local storage sqlite

    }
}

data class LoginViewState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLogin: Boolean = false,
    val navBack: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null
)

sealed class LoginViewAction{
    class Login(val username: String,val password: String) : LoginViewAction()
    class SaveLoginUser(val username: String,val password: String) : LoginViewAction()
    object Logout : LoginViewAction()
    class UploadAvatar(val avatarPath: String) : LoginViewAction()
    object GetUserInfo : LoginViewAction()
    class NavBack(val navHostController: NavHostController) : LoginViewAction()
}