package com.example.picsingular.ui.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.picsingular.bean.CommentLevelFirst
import com.example.picsingular.bean.User
import com.example.picsingular.bean.dto.CommentFirstDTO
import com.example.picsingular.common.constants.HttpConstants
import com.example.picsingular.common.paging.CommonPager
import com.example.picsingular.repository.CommentRepository
import com.example.picsingular.repository.SingularRepository
import com.example.picsingular.repository.UserRepository
import com.example.picsingular.ui.components.items.singular.SingularUserInfoAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingularDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val singularRepository: SingularRepository,
    private val commentRepository: CommentRepository
): ViewModel() {
    private var singularId : Long = -1L
    private val commentDataList by lazy {
        CommonPager { page, size ->
            commentRepository.getFirstCommentList(singularId = singularId ,page, size)
        }
    }

    var singularDetailsState by mutableStateOf(SingularDetailsState(commentDataList = commentDataList))
        private set

    fun intentHandler(action: SingularDetailsAction){
        when (action){
            is SingularDetailsAction.GetUserInfo -> getUserInfo(action.userId)
            is SingularDetailsAction.SubscribeUser -> subscribeUser(action.userId)
            is SingularDetailsAction.UnSubscribeUser -> unSubscribeUser(action.userId)
            is SingularDetailsAction.GetCommentList -> getCommentList(action.singularId)
            is SingularDetailsAction.SendComment -> sendComment(action.singularId, action.content)
            is SingularDetailsAction.AddLikeCount -> addLikeCount(action.singularId)
            is SingularDetailsAction.SubLikeCount -> subLikeCount(action.singularId)
            is SingularDetailsAction.FavoriteSingular -> favoriteSingular(action.singularId)
            is SingularDetailsAction.UnFavoriteSingular -> unFavoriteSingular(action.singularId)
        }
    }

    private fun sendComment(singularId: Long, content: String) {
        viewModelScope.launch {
            val commentFirstDTO = CommentFirstDTO(content = content, singularId = singularId)
            commentRepository.addFirstComment(commentFirstDTO).collect{}
        }
    }

    private fun getUserInfo(userId: Long){
        viewModelScope.launch {
            userRepository.getUserInfoById(userId).collect {res ->
                // update the state
                singularDetailsState = if (res.status == HttpConstants.SUCCESS){
                    singularDetailsState.copy(userInfo = res.data, successGetUserInfo = true, errorUserInfo = "")

                }else{
                    singularDetailsState.copy(userInfo = null, successGetUserInfo = false, errorUserInfo = res.message)
                }
            }
        }
    }

    private fun subscribeUser(userId: Long){
        viewModelScope.launch {
            singularRepository.subscribeUser(userId).collect{res ->
                singularDetailsState = if (res.status == HttpConstants.SUCCESS){
                    singularDetailsState.copy(hasSubscribe = true)
                }else {
                    singularDetailsState.copy(hasSubscribe = false)
                }
            }
        }
    }

    private fun unSubscribeUser(userId: Long) {
        viewModelScope.launch {
            singularRepository.unsubscribeUser(userId).collect{res ->
                singularDetailsState = if (res.status == HttpConstants.SUCCESS){
                    singularDetailsState.copy(hasSubscribe = false)
                }else {
                    singularDetailsState.copy(hasSubscribe = true)
                }
            }
        }
    }

    private fun unFavoriteSingular(singularId: Long) {
        viewModelScope.launch {
            singularRepository.setSingularToUnfavorite(singularId).collect()
        }
    }

    private fun favoriteSingular(singularId: Long) {
        viewModelScope.launch {
            singularRepository.setSingularToFavorite(singularId).collect()
        }
    }

    private fun subLikeCount(singularId: Long) {
        viewModelScope.launch {
            singularRepository.addSingularLikeCount(singularId).collect()
        }
    }

    private fun addLikeCount(singularId: Long) {
        viewModelScope.launch {
            singularRepository.removeSingularLikeCount(singularId).collect()
        }
    }

    private fun getCommentList(singularId: Long){
        this.singularId = singularId
    }
}

data class SingularDetailsState(
    val userInfo: User? = null,
    val successGetUserInfo: Boolean = false,
    val errorUserInfo: String = "",
    val hasSubscribe: Boolean = false,
    val commentIsRefreshing: Boolean = false,
    val commentDataList: Flow<PagingData<CommentLevelFirst>>?,
)

sealed class SingularDetailsAction {
    class SubscribeUser(val userId: Long): SingularDetailsAction()
    class UnSubscribeUser(val userId: Long): SingularDetailsAction()
    class GetUserInfo(val userId: Long): SingularDetailsAction()
    class GetCommentList(val singularId: Long): SingularDetailsAction()
    class SendComment(val singularId: Long, val content: String): SingularDetailsAction()
    data class AddLikeCount(val singularId: Long) : SingularDetailsAction()
    data class SubLikeCount(val singularId: Long) : SingularDetailsAction()
    data class FavoriteSingular(val singularId: Long) : SingularDetailsAction()
    data class UnFavoriteSingular(val singularId: Long) : SingularDetailsAction()
}