package com.example.picsingular.ui.home.community.saved

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.picsingular.bean.Singular
import com.example.picsingular.common.paging.CommonPager
import com.example.picsingular.repository.SingularRepository
import com.example.picsingular.ui.home.community.shared.SharedViewAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val singularRepository: SingularRepository
): ViewModel() {
    private val pageDataList by lazy {
        CommonPager { page, size ->
            singularRepository.getSavedSingularList(page, size)
        }
    }

    var savedViewState by mutableStateOf( SavedViewState(pageDataList = pageDataList))
        private set

    fun intentHandler(action: SavedViewAction){
        when (action){
            is SavedViewAction.ChangeToShared -> setSingularToShared(action.singularId)
        }
    }

    private fun setSingularToShared(singularId: Long){
        viewModelScope.launch {
            singularRepository.setSingularAsShared(singularId = singularId).collect()
        }
    }
}

data class SavedViewState(
    val pageDataList: Flow<PagingData<Singular>>
)

sealed class SavedViewAction(){
    class ChangeToShared(val singularId: Long): SavedViewAction()
}