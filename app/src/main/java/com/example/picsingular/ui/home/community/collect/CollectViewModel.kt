package com.example.picsingular.ui.home.community.collect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.picsingular.bean.Singular
import com.example.picsingular.common.paging.CommonPager
import com.example.picsingular.repository.SingularRepository
import com.example.picsingular.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(
    userRepository: UserRepository,
    singularRepository: SingularRepository
): ViewModel() {
    private val pageDataList by lazy {
        CommonPager { page, size ->
            singularRepository.getFavoriteSingularList(page = page,size = size)
        }
    }

    var favoriteViewState by mutableStateOf(FavoriteViewState(pagingDataList = pageDataList))
        private set
}

data class FavoriteViewState(
    val pagingDataList: Flow<PagingData<Singular>>
)

sealed class FavoriteViewAction(){

}