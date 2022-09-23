package com.example.picsingular.ui.components.items.singular

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picsingular.bean.User
import com.example.picsingular.common.utils.images.ImageUrlUtil
import com.example.picsingular.repository.SingularRepository
import com.example.picsingular.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SingularItemViewModel @Inject constructor(private val userRepository: UserRepository,private val singularRepository: SingularRepository): ViewModel() {
    var userInfoState by mutableStateOf( SingularUserInfoState() )
        private set

    fun singularUserInfoActionHandler(action: SingularUserInfoAction) {
        when (action){
            is SingularUserInfoAction.AddLikeCount -> addLikeCount(action.singularId)
            is SingularUserInfoAction.SubLikeCount -> subLikeCount(action.singularId)
            is SingularUserInfoAction.FavoriteSingular -> favoriteSingular(action.singularId)
            is SingularUserInfoAction.UnFavoriteSingular -> unFavoriteSingular(action.singularId)
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


}

data class SingularUserInfoState(
    val userInfo: User = User(userId = 0, username = "username", avatar = "")
)

sealed class SingularUserInfoAction {
    data class AddLikeCount(val singularId: Long) : SingularUserInfoAction()
    data class SubLikeCount(val singularId: Long) : SingularUserInfoAction()
    data class FavoriteSingular(val singularId: Long) : SingularUserInfoAction()
    data class UnFavoriteSingular(val singularId: Long) : SingularUserInfoAction()
}