package com.example.picsingular.bean.dto

data class CommentSecondDTO(
    val content: String,
    val parentCommentId: Long,
    val parentUserId: Long,
    val replyCommentId: Long,
    val replyUserId: Long,
    val singularId: Long
)
