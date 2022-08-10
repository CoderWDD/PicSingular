package com.example.picsingular.common.utils.retrofit

data class RetrofitResponseBody<T> (
    val status: Int,
    val message: String,
    val data: T
)