package com.example.picsingular.ui.home.community.subscription.userhome

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
class UserHomeViewModel @Inject constructor(
    userRepository: UserRepository,
    singularRepository: SingularRepository
): ViewModel() {
    private var userId: Long = -1L
    private val pageDataList by lazy {
        CommonPager { page, size ->
            singularRepository.getSubscribedSingularListByUserId(userId = userId, page = page, size = size)
        }
    }

    var userHomeViewState by mutableStateOf(UserHomeViewState(singularPageDataList = pageDataList))
        private set

    fun intentHandler(action: UserHomeViewAction){
        when (action){
            is UserHomeViewAction.GetSingularListByUserId -> getSingularPageByUserId(action.userId)
        }
    }

    private fun getSingularPageByUserId(userId: Long){
        this.userId = userId
    }
}

sealed class UserHomeViewAction{
    class GetSingularListByUserId(val userId: Long) : UserHomeViewAction()
}

data class UserHomeViewState(
    val singularPageDataList: Flow<PagingData<Singular>>,
)