package com.example.picsingular.common.utils.retrofit

import android.util.Log
import com.example.picsingular.common.constants.HttpConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
// 动态改变BASE_URL，实现图床切换
object RetrofitPicBedClient {
//    private var BASE_URL = HttpConstants.BASE_PIC_BED_URL

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(PicBedTokenInterceptor())
            .build()
    }

    var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(HttpConstants.BASE_PIC_BED_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun recreatePicBedRetrofitClient(){
        retrofit = Retrofit.Builder()
            .baseUrl(HttpConstants.BASE_PIC_BED_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}