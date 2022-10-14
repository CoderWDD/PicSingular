package com.example.picsingular.service.network.impl

import com.example.picsingular.bean.dto.UserDTO
import com.example.picsingular.bean.dto.UserUpdateDTO
import com.example.picsingular.common.utils.retrofit.RetrofitClient
import com.example.picsingular.service.network.UserNetworkService
import okhttp3.MultipartBody

object UserServiceImpl {
    private val userService = RetrofitClient.retrofit.create(UserNetworkService::class.java)

    suspend fun login(userDTO: UserDTO) = userService.loginUser(userDTO = userDTO)

    suspend fun register(userDTO: UserDTO) = userService.registerUser(userDTO = userDTO)

    suspend fun logout() = userService.logoutUser()

    suspend fun getUserInfo() = userService.getUserInfo()

    suspend fun uploadAvatar(multipartFile: MultipartBody) = userService.uploadAvatar(multipartFile = multipartFile)

    suspend fun updateUserInfo(userUpdateDTO: UserUpdateDTO) = userService.updateUser(userUpdateDTO = userUpdateDTO)

    suspend fun getUserAvatarUrl() = userService.getAvatarUrl()
}