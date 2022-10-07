package com.example.picsingular.ui.home.community.recommend

import androidx.lifecycle.ViewModel
import com.example.picsingular.common.paging.CommonPager
import com.example.picsingular.repository.SingularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.picsingular.bean.Singular
import com.example.picsingular.common.utils.images.ImageUrlUtil
import com.example.picsingular.repository.BannerRepository
import com.example.picsingular.ui.components.banner.BannerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val singularRepository: SingularRepository,
    private val bannerRepository: BannerRepository
) : ViewModel() {
    private val pageDataList by lazy {
        CommonPager { page, size ->
            singularRepository.getAllSingularList(page = page, size = size)
        }
    }

    var recommendPageState by mutableStateOf(RecommendPageState(pageDataList = pageDataList))
        private set

    fun intentHandler(action: RecommendViewAction){
        when (action){
            is RecommendViewAction.GetBannerList -> getBannerList()
        }
    }

    private fun getBannerList(){
        viewModelScope.launch {
            bannerRepository.getBannerList(size = 5).collect{
                val bannerList = mutableListOf<BannerData>()
                it.data?.forEach{ item ->
                    bannerList.add(BannerData(title = "", imageUrl = ImageUrlUtil.getBannerUrl(item.bannerUrl), linkUrl = item.bannerUrl))
                }
                recommendPageState = recommendPageState.copy(bannerList = bannerList)
            }
        }
    }
}

data class RecommendPageState(
    val pageDataList: Flow<PagingData<Singular>>,
    val isRefreshing: Boolean = false,
    val bannerList: List<BannerData> = mutableListOf()
)

sealed class RecommendViewAction{
    object GetBannerList: RecommendViewAction()
}


