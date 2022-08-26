package com.example.picsingular.service.network

import com.example.picsingular.bean.CommentLevelFirst
import com.example.picsingular.bean.CommentLevelSecond
import com.example.picsingular.bean.dto.CommentFirstDTO
import com.example.picsingular.bean.dto.CommentSecondDTO
import com.example.picsingular.bean.dto.PageDTO
import com.example.picsingular.common.utils.retrofit.RetrofitResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentNetworkService {
    @POST("comment/first")
    suspend fun addFirstComment(@Body commentFirstDTO: CommentFirstDTO): RetrofitResponseBody<String>

    @GET("comment/first/{singularId}/{page}/{size}")
    suspend fun getFirstCommentList(
        @Path(value = "singularId") singularId: Long,
        @Path(value = "page") page: Int,
        @Path(value = "size") size: Int
    ): RetrofitResponseBody<PageDTO<CommentLevelFirst>>

    @POST("comment/second")
    suspend fun addSecondComment(@Body commentSecondDTO: CommentSecondDTO): RetrofitResponseBody<List<CommentLevelSecond>>

    @GET("comment/second/{singularId}/{firstCommentId}/{page}/{size}")
    suspend fun getSecondCommentList(
        @Path(value = "singularId") singularId: Long,
        @Path(value = "firstCommentId") firstCommentId: Long,
        @Path(value = "page") page: Int,
        @Path(value = "size") size: Int
    ): RetrofitResponseBody<PageDTO<CommentLevelSecond>>

    @POST("comment/first/like/{firstCommentId}")
    suspend fun plusFirstCommentLikeCount(
        @Path(value = "firstCommentId") firstCommentId: Long
    ): RetrofitResponseBody<Int>

    @POST("comment/first/unlike/{firstCommentId}")
    suspend fun subFirstCommentLikeCount(
        @Path(value = "firstCommentId") firstCommentId: Long
    ): RetrofitResponseBody<Int>

    @POST("comment/second/like/{secondCommentId}")
    suspend fun plusSecondCommentLikeCount(
        @Path(value = "secondCommentId") secondCommentId: Long
    ): RetrofitResponseBody<CommentLevelSecond>

    @POST("comment/second/unlike/{secondCommentId}")
    suspend fun subSecondCommentLikeCount(
        @Path(value = "secondCommentId") secondCommentId: Long
    ): RetrofitResponseBody<CommentLevelSecond>
}