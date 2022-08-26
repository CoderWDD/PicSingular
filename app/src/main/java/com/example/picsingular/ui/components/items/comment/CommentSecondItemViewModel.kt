package com.example.picsingular.ui.components.items.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picsingular.bean.User
import com.example.picsingular.common.constants.HttpConstants
import com.example.picsingular.repository.CommentRepository
import com.example.picsingular.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentSecondItemViewModel @Inject constructor(private val userRepository: UserRepository,private val commentRepository: CommentRepository): ViewModel() {
    var commentSecondItemState by mutableStateOf(CommentSecondItemViewState())
        private set

    fun intentHandler(action: CommentSecondItemAction){
        when (action){
            is CommentSecondItemAction.GetUserInfo -> getUserInfo(action.userId)
        }
    }

    private fun getUserInfo(userId: Long){
        viewModelScope.launch {
            userRepository.getUserInfoById(userId = userId).collect{res ->
                commentSecondItemState = if (res.status == HttpConstants.SUCCESS){
                    commentSecondItemState.copy(userInfo = res.data)
                }else {
                    commentSecondItemState.copy(userInfo = null)
                }
            }
        }
    }
}

data class CommentSecondItemViewState(
    val userInfo: User? = null
)

sealed class CommentSecondItemAction {
    class GetUserInfo(val userId: Long): CommentSecondItemAction()
}