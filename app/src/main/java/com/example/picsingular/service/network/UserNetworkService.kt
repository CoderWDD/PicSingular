package com.example.picsingular.service.network

import com.example.picsingular.bean.User
import com.example.picsingular.bean.dto.UserDTO
import com.example.picsingular.bean.dto.UserUpdateDTO
import com.example.picsingular.common.utils.retrofit.RetrofitResponseBody
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.POST

interface UserNetworkService {
    @POST("user/register")
    suspend fun registerUser(userDTO: UserDTO): RetrofitResponseBody<String>

    @POST("user/login")
    suspend fun loginUser(userDTO: UserDTO): RetrofitResponseBody<User>

    @POST("user/logout")
    suspend fun logoutUser(): RetrofitResponseBody<String>

    @GET("user/info")
    suspend fun getUserInfo(): RetrofitResponseBody<User>

    @POST("user/avatar")
    suspend fun uploadAvatar(avatar: MultipartBody.Part): RetrofitResponseBody<User>

//    @GET("user/avatar")
//    suspend fun getAvatar(): RetrofitResponseBody<MultipartBody.Part>

    @POST("user/update")
    suspend fun updateUser(userUpdateDTO: UserUpdateDTO): RetrofitResponseBody<User>

    @GET("user/avatar/url")
    suspend fun getAvatarUrl(): RetrofitResponseBody<String>
}