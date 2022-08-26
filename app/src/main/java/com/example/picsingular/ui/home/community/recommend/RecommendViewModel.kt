package com.example.picsingular.ui.home.community.recommend

import androidx.lifecycle.ViewModel
import com.example.picsingular.common.paging.CommonPager
import com.example.picsingular.repository.SingularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.PagingData
import com.example.picsingular.bean.Singular
import kotlinx.coroutines.flow.Flow


@HiltViewModel
class RecommendViewModel @Inject constructor(private val singularRepository: SingularRepository) : ViewModel() {
    private val pageDataList by lazy {
        CommonPager{page,size->
            singularRepository.getAllSingularList(page = page, size = size)
        }
    }

    var recommendPageState by mutableStateOf( RecommendPageState(pageDataList = pageDataList) )
        private set
}

data class RecommendPageState(
    val pageDataList: Flow<PagingData<Singular>>,
    val isRefreshing: Boolean = false
)

