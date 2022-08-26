package com.example.picsingular.ui.home.community.subscription

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.picsingular.bean.Singular
import com.example.picsingular.bean.User
import com.example.picsingular.common.constants.HttpConstants
import com.example.picsingular.common.paging.CommonPager
import com.example.picsingular.repository.SingularRepository
import com.example.picsingular.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val singularRepository: SingularRepository
): ViewModel() {
    private val pageDataList by lazy{
        CommonPager { page, size ->
            singularRepository.getSubscribedUserList(page = page, size = size)
        }
    }

    var subscriptionViewState by mutableStateOf(SubscriptionViewState(pageDataList = pageDataList))
        private set

    fun intentHandler(action: SubscriptionViewAction){
        when (action){
            is SubscriptionViewAction.UnSubscribeUser -> unSubscribeUser(action.userId)
        }
    }

    private fun unSubscribeUser(userId: Long){
        viewModelScope.launch {
            singularRepository.unsubscribeUser(userId = userId).collect()
        }
    }
}

data class SubscriptionViewState(
    val isRefreshing: Boolean = false,
    val pageDataList: Flow<PagingData<User>>,

)

sealed class SubscriptionViewAction(){
    class UnSubscribeUser(val userId: Long) : SubscriptionViewAction()
}