package com.example.picsingular.bean.dto

data class UserUpdateDTO (
    val username: String,
    val password: String,
    val avatar: String? = null,
    val signature: String? = null
)