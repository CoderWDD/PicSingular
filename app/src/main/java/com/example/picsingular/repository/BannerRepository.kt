package com.example.picsingular.repository

import com.example.picsingular.common.utils.retrofit.ApiCallHandler
import com.example.picsingular.service.network.BannerNetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BannerRepository @Inject constructor(private val bannerNetworkService: BannerNetworkService){
    fun getBannerList(size: Int) = flow {
        val bannerListRes = ApiCallHandler.apiCall { bannerNetworkService.getBannerList(size) }
        emit(bannerListRes)
    }.flowOn(Dispatchers.IO)
}