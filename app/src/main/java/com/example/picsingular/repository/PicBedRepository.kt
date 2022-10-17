package com.example.picsingular.repository

import com.example.picsingular.common.utils.retrofit.ApiCallHandler
import com.example.picsingular.service.network.PicBedNetworkService
import com.example.picsingular.service.storage.sharedpreference.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicBedRepository @Inject constructor(private val picBedNetworkService: PicBedNetworkService, private val dataStoreManager: DataStoreManager){
    suspend fun uploadImageToPicBed(multipartFiles: MultipartBody) = flow {
        val uploadRes = ApiCallHandler.apiCall { picBedNetworkService.uploadImagesToPicBed(multipartFiles) }
        emit(uploadRes)
    }.flowOn(Dispatchers.IO)

    suspend fun getPicBedImagesList(page: Int, size: Int)  = ApiCallHandler.apiCall { picBedNetworkService.getPicBedImagesList(page = page, size = size) }

    suspend fun savePicBedConfigToLocalStore(baseUrl: String, token: String) {
        dataStoreManager.savePicBedConfig(picBedBaseUrl = baseUrl, picBedToken = token)
    }

    suspend fun deleteImage(imagePath: String) = flow {
        val deleteRes = ApiCallHandler.apiCall { picBedNetworkService.deleteImage(imagePath = imagePath) }
        emit(deleteRes)
    }.flowOn(Dispatchers.IO)

    fun getPicBedToken() : Flow<String> = dataStoreManager.getPicBedToken()

    fun getPicBedBaseUrl(): Flow<String> = dataStoreManager.getPicBedBaseUrl()
}