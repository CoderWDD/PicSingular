package com.example.picsingular.service.network

import com.example.picsingular.bean.dto.BannerDTO
import com.example.picsingular.common.utils.retrofit.RetrofitResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface BannerNetworkService {
    @GET("banner/list/{size}")
    suspend fun getBannerList(@Path("size") size: Int): RetrofitResponseBody<List<BannerDTO>>
}