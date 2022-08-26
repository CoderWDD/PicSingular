package com.example.picsingular.bean

data class CommentLevelSecond(
    val commentSecondId: Long,
    val singularId: Long,
    val content: String,
    val likeCount: Int,
    val repliedCommentId: Long,
    val repliedUserId: Long,
    val repliedUserName: String,
    val parentCommentId: Long,
    val parentUserId: Long,
    val parentUserName: String,
    val createDate: String,
    val userId: Long
)
