package com.example.picsingular.bean

data class User (
    val username: String,
    val password: String,
    val avatar: String? = null,
    val signature: String? = null,
)