package com.example.picsingular.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picsingular.bean.User
import com.example.picsingular.common.constants.HttpConstants
import com.example.picsingular.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var userRepository: UserRepository) : ViewModel() {
    var loginStatus by mutableStateOf(LoginViewState())
        private set

    fun IntentHandler(action: LoginViewAction){
        when (action){
            is LoginViewAction.Login -> login(username = action.username, password = action.password)
        }
    }

    private fun login(username: String, password: String) {
        viewModelScope.launch {
            userRepository.login(username = username, password = password).collect{res ->
                loginStatus = if (res.status == HttpConstants.SUCCESS){
                    loginStatus.copy(isSuccess = true, user = res.data)
                }else {
                    loginStatus.copy(isError = true, errorMessage = res.message)
                }
            }
        }
    }
}

data class LoginViewState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null
)

sealed class LoginViewAction{
    class Login(val username: String,val password: String) : LoginViewAction()
}