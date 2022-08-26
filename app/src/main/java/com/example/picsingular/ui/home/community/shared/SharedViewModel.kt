package com.example.picsingular.ui.home.community.shared

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.picsingular.bean.Singular
import com.example.picsingular.common.paging.CommonPager
import com.example.picsingular.repository.SingularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val singularRepository: SingularRepository
): ViewModel(){
    private val pageDataList by lazy {
        CommonPager { page, size ->
            singularRepository.getSharedSingularList(page, size)
        }
    }

    var sharedViewState by mutableStateOf( SharedViewState(pageDataList = pageDataList) )
        private set

    fun intentHandler(action: SharedViewAction){
        when (action){
            is SharedViewAction.ChangeToSaved -> setSingularToSaved(action.singularId)
        }
    }

    private fun setSingularToSaved(singularId: Long){
        viewModelScope.launch {
            singularRepository.setSingularAsSaved(singularId = singularId).collect()
        }
    }
}

data class SharedViewState(
    val pageDataList: Flow<PagingData<Singular>>
)

sealed class SharedViewAction(){
    class ChangeToSaved(val singularId: Long): SharedViewAction()
}