package com.example.picsingular.ui.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picsingular.bean.User
import com.example.picsingular.common.constants.HttpConstants
import com.example.picsingular.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    private val _viewEvent = Channel<RegisterEvent> (Channel.BUFFERED)
    val viewEvent = _viewEvent.receiveAsFlow()

    fun intentHandler(action: RegisterAction){
        when (action){
            is RegisterAction.Register -> register(username = action.username, password = action.password)
        }
    }

    private fun register(username: String, password: String){
        viewModelScope.launch {
            userRepository.register(username = username, password = password).collect{res ->
                if (res.status == HttpConstants.SUCCESS){
                    // 注册成功就返回
                    _viewEvent.send(RegisterEvent.NavBack)
                    _viewEvent.send(RegisterEvent.MessageEvent(res.message))
                }
            }
        }
    }
}

sealed class RegisterAction{
    class Register(val username: String, val password: String): RegisterAction()
}

sealed class RegisterEvent{
    object NavBack : RegisterEvent()
    class MessageEvent(val msg: String) : RegisterEvent()
}