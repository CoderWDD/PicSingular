package com.example.picsingular.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class CommonPagingSource<T: Any,K: Any> (private val pagingServiceCall: suspend (LoadParams<T>) -> LoadResult<T,K>) : PagingSource<T, K>() {
    override fun getRefreshKey(state: PagingState<T, K>): T? {
        return null
    }

    override suspend fun load(params: LoadParams<T>): LoadResult<T, K> {
        return pagingServiceCall(params)
    }
}