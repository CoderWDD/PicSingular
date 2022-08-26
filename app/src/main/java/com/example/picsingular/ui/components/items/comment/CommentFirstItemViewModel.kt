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
class CommentFirstItemViewModel @Inject constructor(private val commentRepository: CommentRepository, private val userRepository: UserRepository) : ViewModel() {
//    private val userInfo by lazy { userRepository.getUserInfoById() }

    var commentFirstItemState by mutableStateOf( CommentFirstItemState())
        private set

    fun intentHandler(action: CommentFirstItemAction){
        when (action) {
            is CommentFirstItemAction.GetUserInfo -> getUserInfo(action.userId)
        }
    }

    // 获取一级评论用户的信息：头像，username
    private fun getUserInfo(userId: Long){
        viewModelScope.launch {
            userRepository.getUserInfoById(userId = userId).collect{ res ->
                commentFirstItemState = if (res.status == HttpConstants.SUCCESS){
                    commentFirstItemState.copy(userInfo = res.data)
                }else {
                    commentFirstItemState.copy(userInfo = null)
                }
            }
        }
    }
}

data class CommentFirstItemState(
    val userInfo: User? = null
)

sealed class CommentFirstItemAction(){
    class GetUserInfo(val userId: Long) : CommentFirstItemAction()
}