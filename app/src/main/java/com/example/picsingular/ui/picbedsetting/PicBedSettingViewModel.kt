package com.example.picsingular.ui.picbedsetting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picsingular.common.constants.HttpConstants
import com.example.picsingular.common.constants.TokenConstants
import com.example.picsingular.repository.PicBedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PicBedSettingViewModel @Inject constructor(private val picBedRepository: PicBedRepository): ViewModel() {
    var viewState by mutableStateOf(PicBedSettingState())
        private set

    fun intentHandler(action: PicBedSettingAction){
        when(action){
            is PicBedSettingAction.SavePicBedConfig -> savePicBedConfig(baseUrl = action.baseUrl, token = action.token)
            is PicBedSettingAction.GetPicBedConfig -> getPicBedConfig()
        }
    }

    private fun savePicBedConfig(baseUrl: String, token: String){
        viewModelScope.launch { picBedRepository.savePicBedConfigToLocalStore(baseUrl = baseUrl, token = token) }
    }

    private fun getPicBedConfig(){
        viewModelScope.launch{
            picBedRepository.getPicBedToken().collect{
                if (it.isNotEmpty()){
                    viewState = viewState.copy(token = it)
                    TokenConstants.PIC_BED_TOKEN = it
                }
            }
            picBedRepository.getPicBedBaseUrl().collect{
                if (it.isNotEmpty()){
                    viewState = viewState.copy(baseUrl = it)
                    HttpConstants.BASE_PIC_BED_URL = it
                }
            }
        }
    }
}

data class PicBedSettingState(
    val baseUrl: String? = null,
    val token: String? = null
)

sealed class PicBedSettingAction(){
    class SavePicBedConfig(val baseUrl: String, val token: String): PicBedSettingAction()
    object GetPicBedConfig : PicBedSettingAction()
}