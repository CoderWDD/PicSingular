package com.example.picsingular.service.network

import com.example.picsingular.bean.Singular
import com.example.picsingular.bean.User
import com.example.picsingular.bean.dto.PageDTO
import com.example.picsingular.bean.dto.SingularDTO
import com.example.picsingular.common.utils.retrofit.RetrofitResponseBody
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SingularNetworkService {
    @POST("singular/create")
    suspend fun createSingular(@Body singularDTO: SingularDTO): RetrofitResponseBody<Singular>

    @POST("singular/upload")
    suspend fun uploadImagesToSingular(multipartFileList: MutableList<MultipartBody.Part>): RetrofitResponseBody<List<String>>

//    展示图片，可以直接将url拼接，用coil直接展示
//    @GET("/singular/image/{singularId}/{url}")
//    suspend fun getImage(@Path("singularId") singularId: String, @Path("url") url: String)

    @GET("singular/list/saved/{page}/{size}")
    suspend fun getSavedSingularList(@Path("page") page: Int, @Path("size") size: Int): RetrofitResponseBody<PageDTO<Singular>>

    @GET("singular/list/shared/{page}/{size}")
    suspend fun getSharedSingularList(@Path("page") page: Int, @Path("size") size: Int): RetrofitResponseBody<PageDTO<Singular>>

    @GET("singular/list/all/{page}/{size}")
    suspend fun getAllSingularList(@Path("page") page: Int, @Path("size") size: Int): RetrofitResponseBody<PageDTO<Singular>>

    @POST("singular/change/shared/{singularId}")
    suspend fun changeSingularStatusToShared(@Path("singularId") singularId: Long): RetrofitResponseBody<Singular>

    @POST("singular/change/saved/{singularId}")
    suspend fun changeSingularStatusToSaved(@Path("singularId") singularId: Long): RetrofitResponseBody<Singular>

    @POST("singular/delete/{singularId}")
    suspend fun deleteSingular(@Path("singularId") singularId: Long): RetrofitResponseBody<Singular>

    @GET("singular/get/{singularId}")
    suspend fun getSingular(@Path("singularId") singularId: Long): RetrofitResponseBody<Singular>

    @POST("singular/favorite/{singularId}")
    suspend fun favoriteSingular(@Path("singularId") singularId: Long): RetrofitResponseBody<Singular>

    @POST("singular/unfavorite/{singularId}")
    suspend fun unfavoriteSingular(@Path("singularId") singularId: Long): RetrofitResponseBody<Singular>

    @GET("singular/favorite/list/{page}/{size}")
    suspend fun getFavoriteSingularList(@Path("page") page: Int, @Path("size") size: Int): RetrofitResponseBody<PageDTO<Singular>>

    @POST("singular/subscribe/{userId}")
    suspend fun subscribeUser(@Path("userId") userId: Long): RetrofitResponseBody<String>

    @POST("singular/unsubscribe/{userId}")
    suspend fun unsubscribeUser(@Path("userId") userId: Long): RetrofitResponseBody<String>

    @GET("singular/subscribe/list/{page}/{size}")
    suspend fun getSubscribeUserList(@Path("page") page: Int, @Path("size") size: Int): RetrofitResponseBody<PageDTO<User>>

    @GET("singular/subscribe/{userId}/{page}/{size}")
    suspend fun getSubscribeSingularListByUserId(@Path("userId") userId: Long, @Path("page") page: Int, @Path("size") size: Int): RetrofitResponseBody<PageDTO<Singular>>

    @GET("singular/subscribe/all/{page}/{size}")
    suspend fun getSubscribeSingularList(@Path("page") page: Int, @Path("size") size: Int): RetrofitResponseBody<PageDTO<Singular>>

    @GET("singular/hasSubscribed/{userId}")
    suspend fun getHasSubscribedUser(@Path("userId") userId: Long): RetrofitResponseBody<Boolean>

    @POST("singular/read/{singularId}")
    suspend fun readSingular(@Path("singularId") singularId: Long): RetrofitResponseBody<Singular>

    @POST("singular/like/{singularId}")
    suspend fun likeSingular(@Path("singularId") singularId: Long): RetrofitResponseBody<Singular>

    @POST("singular/unlike/{singularId}")
    suspend fun unlikeSingular(@Path("singularId") singularId: Long): RetrofitResponseBody<Singular>
}