package com.example.picsingular.service.network

import com.example.picsingular.bean.dto.PageDTO
import com.example.picsingular.common.utils.retrofit.RetrofitResponseBody
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PicBedNetworkService {
    @POST("/images/upload")
    suspend fun uploadImagesToPicBed(@Body multipartFiles: MultipartBody): RetrofitResponseBody<List<String>>

    @GET("/list/{page}/{size}")
    suspend fun getPicBedImagesList(@Path("page") page: Int, @Path("size") size: Int): RetrofitResponseBody<PageDTO<String>>

    @POST("/images/delete/{imagePath}")
    suspend fun deleteImage(@Path("imagePath") imagePath: String): RetrofitResponseBody<String>
}