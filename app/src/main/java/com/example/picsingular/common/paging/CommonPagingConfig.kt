package com.example.picsingular.common.paging

import androidx.paging.PagingConfig

data class CommonPagingConfig(
    val pageSize: Int = 15,
    val initialLoadSize: Int = 15,
    val enablePlaceholders: Boolean = false,
    val prefetchDistance: Int = 1,
    val maxSize: Int = PagingConfig.MAX_SIZE_UNBOUNDED
)