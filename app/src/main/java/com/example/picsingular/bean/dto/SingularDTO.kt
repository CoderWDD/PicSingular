package com.example.picsingular.bean.dto

data class SingularDTO (
    val content: String,
    val status: String,
    val title: String,
    val category: List<String>,
    val imagesUrl: List<String>
)