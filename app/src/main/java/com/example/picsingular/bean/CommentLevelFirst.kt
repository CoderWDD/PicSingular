package com.example.picsingular.bean

data class CommentLevelFirst(
    val commentLevelId: Long,
    val singularId: Long,
    val userId: Long,
    val username: String,
    val content: String,
    val likeCount: Int,
    val createDate: String,
    val commentSecondList: List<CommentLevelSecond>,
    val avatar: String
)
