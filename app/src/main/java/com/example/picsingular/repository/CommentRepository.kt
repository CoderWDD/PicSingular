package com.example.picsingular.repository

import com.example.picsingular.bean.dto.CommentFirstDTO
import com.example.picsingular.bean.dto.CommentSecondDTO
import com.example.picsingular.common.utils.retrofit.ApiCallHandler
import com.example.picsingular.service.network.CommentNetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentRepository @Inject constructor(private val commentNetworkService: CommentNetworkService) {
    fun addFirstComment(commentFirstDTO: CommentFirstDTO) = flow {
        val addCommentFirstRes = ApiCallHandler.apiCall{ commentNetworkService.addFirstComment(commentFirstDTO) }
        emit(addCommentFirstRes)
    }.flowOn(Dispatchers.IO)

    suspend fun getFirstCommentList(singularId: Long, page: Int, size: Int) = ApiCallHandler.apiCall{ commentNetworkService.getFirstCommentList(singularId, page, size) }

    fun addSecondComment(commentSecondDTO: CommentSecondDTO) = flow {
        val addCommentSecondRes = ApiCallHandler.apiCall{ commentNetworkService.addSecondComment(commentSecondDTO) }
        emit(addCommentSecondRes)
    }.flowOn(Dispatchers.IO)

    fun getSecondCommentList(singularId: Long, firstCommentId: Long, page: Int, size: Int) = flow {
        val getCommentSecondRes = ApiCallHandler.apiCall{ commentNetworkService.getSecondCommentList(singularId, firstCommentId, page, size) }
        emit(getCommentSecondRes)
    }.flowOn(Dispatchers.IO)

    fun plusFirsCommentLikeCount(firstCommentId: Long) = flow {
        val plusFirstCommentLikeCountRes = ApiCallHandler.apiCall{ commentNetworkService.plusFirstCommentLikeCount(firstCommentId) }
        emit(plusFirstCommentLikeCountRes)
    }.flowOn(Dispatchers.IO)

    fun subFirsCommentLikeCount(firstCommentId: Long) = flow {
        val subFirstCommentLikeCountRes = ApiCallHandler.apiCall{ commentNetworkService.subFirstCommentLikeCount(firstCommentId) }
        emit(subFirstCommentLikeCountRes)
    }.flowOn(Dispatchers.IO)

    fun plusSecondCommentLikeCount(secondCommentId: Long) = flow {
        val plusSecondCommentLikeCountRes = ApiCallHandler.apiCall{ commentNetworkService.plusSecondCommentLikeCount(secondCommentId) }
        emit(plusSecondCommentLikeCountRes)
    }.flowOn(Dispatchers.IO)

    fun subSecondCommentLikeCount(secondCommentId: Long) = flow {
        val subSecondCommentLikeCountRes = ApiCallHandler.apiCall{ commentNetworkService.subSecondCommentLikeCount(secondCommentId) }
        emit(subSecondCommentLikeCountRes)
    }.flowOn(Dispatchers.IO)


}