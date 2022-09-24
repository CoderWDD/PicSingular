package com.example.picsingular.repository

import android.util.Log
import com.example.picsingular.bean.dto.CommentFirstDTO
import com.example.picsingular.bean.dto.SingularDTO
import com.example.picsingular.common.utils.retrofit.ApiCallHandler
import com.example.picsingular.service.network.SingularNetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingularRepository @Inject constructor(private val singularNetworkService: SingularNetworkService){
    // create a singular
    fun createSingular(singularDTO: SingularDTO) = flow {
        val createRes = ApiCallHandler.apiCall { singularNetworkService.createSingular(singularDTO) }
        emit(createRes)
    }.flowOn(Dispatchers.IO)

    // get all saved status singulars under current user
    suspend fun getSavedSingularList(page: Int, size: Int) = ApiCallHandler.apiCall { singularNetworkService.getSavedSingularList(page, size) }


    // get all saved status singulars under current user
    suspend fun getSharedSingularList(page: Int, size: Int) = ApiCallHandler.apiCall { singularNetworkService.getSharedSingularList(page, size) }

    // get all singulars
    suspend fun getAllSingularList(page: Int, size: Int) = ApiCallHandler.apiCall { singularNetworkService.getAllSingularList(page, size) }


    // set singular as shared
    fun setSingularAsShared(singularId: Long) = flow {
        val setAsShared = ApiCallHandler.apiCall { singularNetworkService.changeSingularStatusToShared(singularId) }
        emit(setAsShared)
    }.flowOn(Dispatchers.IO)

    // set singular as saved
    fun setSingularAsSaved(singularId: Long) = flow {
        val setAsShared = ApiCallHandler.apiCall { singularNetworkService.changeSingularStatusToSaved(singularId) }
        emit(setAsShared)
    }.flowOn(Dispatchers.IO)

    // delete singular
    fun deleteSingular(singularId: Long) = flow {
        val delete = ApiCallHandler.apiCall { singularNetworkService.deleteSingular(singularId) }
        emit(delete)
    }.flowOn(Dispatchers.IO)

    // get singular by id
    fun getSingularById(singularId: Long) = flow {
        val singular = ApiCallHandler.apiCall { singularNetworkService.getSingular(singularId)}
        emit(singular)
    }.flowOn(Dispatchers.IO)

    // set singular to favorite
    fun setSingularToFavorite(singularId: Long) = flow {
        val setToFavorite = ApiCallHandler.apiCall { singularNetworkService.favoriteSingular(singularId) }
        emit(setToFavorite)
    }.flowOn(Dispatchers.IO)

    // set singular to unfavorite
    fun setSingularToUnfavorite(singularId: Long) = flow {
        val setToUnfavorite = ApiCallHandler.apiCall { singularNetworkService.unfavoriteSingular(singularId) }
        emit(setToUnfavorite)
    }.flowOn(Dispatchers.IO)

    // get favorite singular list
    suspend fun getFavoriteSingularList(page: Int, size: Int) = ApiCallHandler.apiCall { singularNetworkService.getFavoriteSingularList(page, size) }

    // subscribe user
    fun subscribeUser(userId: Long) = flow {
        val subscribeRes = ApiCallHandler.apiCall { singularNetworkService.subscribeUser(userId) }
        emit(subscribeRes)
    }.flowOn(Dispatchers.IO)

    // unsubscribe user
    fun unsubscribeUser(userId: Long) = flow {
        val unsubscribeRes = ApiCallHandler.apiCall { singularNetworkService.unsubscribeUser(userId) }
        emit(unsubscribeRes)
    }.flowOn(Dispatchers.IO)

    fun checkHasSubscribedUser(userId: Long) = flow {
        val hasSubscribedUser = ApiCallHandler.apiCall { singularNetworkService.getHasSubscribedUser(userId = userId) }
        emit(hasSubscribedUser)
    }.flowOn(Dispatchers.IO)

    // get subscribed user list
    suspend fun getSubscribedUserList(page: Int, size: Int) = ApiCallHandler.apiCall { singularNetworkService.getSubscribeUserList(page, size) }

    // get subscribed singular list by user id
    suspend fun getSubscribedSingularListByUserId(userId: Long, page: Int, size: Int) = ApiCallHandler.apiCall { singularNetworkService.getSubscribeSingularListByUserId(userId, page, size) }

    // get all subscribed singular list
    fun getAllSubscribedSingularList(page: Int, size: Int) = flow {
        val allSubscribedSingularList = ApiCallHandler.apiCall { singularNetworkService.getSubscribeSingularList(page, size) }
        emit(allSubscribedSingularList)
    }.flowOn(Dispatchers.IO)

    // set singular read
    fun setSingularRead(singularId: Long) = flow {
        val setRead = ApiCallHandler.apiCall { singularNetworkService.readSingular(singularId) }
        emit(setRead)
    }.flowOn(Dispatchers.IO)

    // add singular like count
    fun addSingularLikeCount(singularId: Long) = flow {
        val addLikeCount = ApiCallHandler.apiCall { singularNetworkService.likeSingular(singularId) }
        emit(addLikeCount)
    }.flowOn(Dispatchers.IO)

    // remove singular like count
    fun removeSingularLikeCount(singularId: Long) = flow {
        val removeLikeCount = ApiCallHandler.apiCall { singularNetworkService.unlikeSingular(singularId) }
        emit(removeLikeCount)
    }.flowOn(Dispatchers.IO)


}