package com.example.picsingular.service.network

import com.example.picsingular.bean.User
import com.example.picsingular.bean.dto.UserDTO
import com.example.picsingular.bean.dto.UserUpdateDTO
import com.example.picsingular.common.utils.retrofit.RetrofitResponseBody
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserNetworkService {
    @POST("user/register")
    suspend fun registerUser(@Body userDTO: UserDTO): RetrofitResponseBody<String>

    @POST("user/login")
    suspend fun loginUser(@Body userDTO: UserDTO): RetrofitResponseBody<User?>

    @POST("user/logout")
    suspend fun logoutUser(): RetrofitResponseBody<String>

    @GET("user/info")
    suspend fun getUserInfo(): RetrofitResponseBody<User>

    @GET("user/info/{id}")
    suspend fun getUserInfoById(@Path(value = "id") userId: Long): RetrofitResponseBody<User>

    @Multipart
    @POST("user/avatar")
    suspend fun uploadAvatar(@Part avatar: MultipartBody.Part): RetrofitResponseBody<User>

//    @GET("user/avatar")
//    suspend fun getAvatar(): RetrofitResponseBody<MultipartBody.Part>

    @POST("user/update")
    suspend fun updateUser(@Body userUpdateDTO: UserUpdateDTO): RetrofitResponseBody<User>

    @GET("user/avatar/url")
    suspend fun getAvatarUrl(): RetrofitResponseBody<String>
}