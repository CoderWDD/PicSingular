package com.example.picsingular.common.paging

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.picsingular.bean.dto.PageDTO
import com.example.picsingular.common.constants.HttpConstants
import com.example.picsingular.common.utils.retrofit.RetrofitResponseBody
import kotlinx.coroutines.flow.Flow

// an expand function of viewModel, which can provide the page flow
fun <T : Any> ViewModel.CommonPager(
    config: CommonPagingConfig = CommonPagingConfig(),
    // the call been handle by ApiExceptionHandler
    serviceCall: suspend (page: Int, size: Int) -> RetrofitResponseBody<PageDTO<T>>
): Flow<PagingData<T>> {
    val pagingConfig = PagingConfig(
        pageSize = config.pageSize,
        enablePlaceholders = config.enablePlaceholders,
        initialLoadSize = config.initialLoadSize,
        prefetchDistance = config.prefetchDistance,
        maxSize = config.maxSize
    )

    return Pager(config = pagingConfig, initialKey = 1) {
        CommonPagingSource {
            // page count from 1 to n
            val page = it.key ?: 1
            val pageSize = it.loadSize
            // this call will return the result which had handle by ApiExceptionHandler
            val responseRes = serviceCall.invoke(page, pageSize)
            if (responseRes.status == HttpConstants.SUCCESS) {
                // if the status is success
                val data = responseRes.data!!.dataList ?: emptyList()
                val hasMore = responseRes.data.hasMore
                return@CommonPagingSource PagingSource.LoadResult.Page(
                    data = data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (hasMore) page + 1 else null
                )
            } else {
                return@CommonPagingSource PagingSource.LoadResult.Error(Exception(responseRes.message))
            }
        }
    }.flow.cachedIn(viewModelScope)
}