package com.example.picsingular

import android.app.Application
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.picsingular.bean.User
import com.example.picsingular.common.constants.TokenConstants
import com.example.picsingular.repository.UserRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltAndroidApp
class App: Application(){

    companion object{
        var globalUserInfo: MutableState<User?> = mutableStateOf(null)
        var isLogin: MutableState<Boolean> = mutableStateOf(false)
        var appState by mutableStateOf(AppState())
            private set

        fun AppActionHandler(action: AppAction){
            when (action){
                is AppAction.UpdateUserInfo -> updateUserInfo(action.userInfo)
                is AppAction.UpdateLoginState -> updateLoginState(action.isLogin)
            }
        }

        private fun updateUserInfo(userInfo: User?){
            appState = appState.copy(userInfo = userInfo)
        }

        private fun updateLoginState(isLogin: Boolean){
            appState = appState.copy(isLogin = isLogin)
        }

    }

    @Inject
    lateinit var userRepository: UserRepository
    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.getTokenFromLocalStore().collect{
                // 只有非空才更新，否则在登录时出现登录获取的token被这里的空token覆盖，导致需要登两次的bug
                if (it.isNotEmpty()){
                    TokenConstants.TOKEN = it
                }
            }
        }
    }
}

data class AppState(
    val userInfo: User? = null,
    val isLogin: Boolean = false
)

sealed class AppAction(){
    class UpdateUserInfo(val userInfo: User?): AppAction()
    class UpdateLoginState(val isLogin: Boolean): AppAction()
}