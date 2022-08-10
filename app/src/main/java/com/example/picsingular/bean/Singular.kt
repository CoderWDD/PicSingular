package com.example.picsingular.bean

data class Singular (
    val singularId: Long,
    val likeCount: Int,
    val commentCount: Int,
    val readCount: Int,
    val description: String,
    val singularStatus: String,
    val imageList: List<String>? = null,
    val pushDate: String
)