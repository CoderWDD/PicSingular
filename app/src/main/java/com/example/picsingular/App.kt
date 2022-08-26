package com.example.picsingular

import android.app.Application
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
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
        var globalUserInfo: User? = null
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