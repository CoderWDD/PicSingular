package com.example.picsingular.ui.picbedsetting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picsingular.common.constants.HttpConstants
import com.example.picsingular.common.constants.TokenConstants
import com.example.picsingular.common.utils.retrofit.RetrofitPicBedClient
import com.example.picsingular.repository.PicBedRepository
import com.example.picsingular.ui.login.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PicBedSettingViewModel @Inject constructor(private val picBedRepository: PicBedRepository): ViewModel() {
    var viewState by mutableStateOf(PicBedSettingState())
        private set

    private val _viewEvent = Channel<PicBedSettingEvent> (capacity = Channel.BUFFERED)
    val viewEvent = _viewEvent.receiveAsFlow()

    fun intentHandler(action: PicBedSettingAction){
        when(action){
            is PicBedSettingAction.SavePicBedConfig -> savePicBedConfig(baseUrl = action.baseUrl, token = action.token)
            is PicBedSettingAction.GetPicBedConfig -> getPicBedConfig()
        }
    }

    private fun savePicBedConfig(baseUrl: String, token: String){
        viewModelScope.launch {
            picBedRepository.savePicBedConfigToLocalStore(baseUrl = baseUrl, token = token)
            // 刷新配置信息，并重新创建 PicBed Retrofit Client
            HttpConstants.BASE_PIC_BED_URL = baseUrl
            TokenConstants.PIC_BED_TOKEN = token
            RetrofitPicBedClient.recreatePicBedRetrofitClient()
            // 发送 event 给 view
            _viewEvent.send(PicBedSettingEvent.MessageEvent(msg = "图床配置更新成功！"))
        }
    }

    private fun getPicBedConfig(){
        viewModelScope.launch{
            picBedRepository.getPicBedToken().collect{
                if (it.isNotEmpty()){
                    viewState = viewState.copy(token = it)
                    TokenConstants.PIC_BED_TOKEN = it
                    // 发送 event 给 view
                    _viewEvent.send(PicBedSettingEvent.MessageEvent(msg = "图床密钥读取成功！"))
                }else{
                    // 发送 event 给 view
                    _viewEvent.send(PicBedSettingEvent.MessageEvent(msg = "图床密钥读取失败！"))
                }
            }
            picBedRepository.getPicBedBaseUrl().collect{
                if (it.isNotEmpty()){
                    viewState = viewState.copy(baseUrl = it)
                    HttpConstants.BASE_PIC_BED_URL = it
                    _viewEvent.send(PicBedSettingEvent.MessageEvent(msg = "图床URL读取成功！"))
                }else{
                    _viewEvent.send(PicBedSettingEvent.MessageEvent(msg = "图床URL读取失败！"))
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

sealed class PicBedSettingEvent(){
    class MessageEvent(val msg: String): PicBedSettingEvent()
}