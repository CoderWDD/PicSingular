package com.example.picsingular.bean.dto

data class PageDTO<T> (
    val totalPages: Int,
    val totalElements: Long,
    val currentPage: Int,
    val currentElements: Int,
    val pageSize: Int,
    val hasMore: Boolean,
    val isFirst: Boolean,
    val isLast: Boolean,
    val hasPrevious: Boolean,
    val dataList: List<T>?
)