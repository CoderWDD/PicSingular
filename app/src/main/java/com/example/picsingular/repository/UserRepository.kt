package com.example.picsingular.repository

import com.example.picsingular.bean.dto.UserDTO
import com.example.picsingular.service.network.impl.UserServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository {
    fun login(username: String, password: String) = flow {
        val userDTO = UserDTO(username = username, password = password)
        val loginRes = UserServiceImpl.login(userDTO = userDTO)
        emit(loginRes)
    }.flowOn(Dispatchers.IO)

}