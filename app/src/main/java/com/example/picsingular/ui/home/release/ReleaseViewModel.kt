package com.example.picsingular.ui.home.release

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picsingular.bean.Singular
import com.example.picsingular.bean.dto.SingularDTO
import com.example.picsingular.common.constants.HttpConstants
import com.example.picsingular.common.constants.SingularConstants
import com.example.picsingular.common.utils.retrofit.RetrofitResponseBody
import com.example.picsingular.repository.SingularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val singularRepository: SingularRepository
): ViewModel() {
    var releasePageState by mutableStateOf(ReleasePageState())
        private set

    fun intentHandler(action: ReleasePageAction){
        when (action){
            is ReleasePageAction.ReleaseSingular -> releaseSingular(action.singularInfo)
            is ReleasePageAction.SaveSingular -> saveSingular(action.singularInfo)
        }
    }

    private fun releaseSingular(singularInfo: SingularInfo){
        saveOrReleaseSingular(singularInfo = singularInfo, status = SingularConstants.SHARED.name)
    }

    private fun saveSingular(singularInfo: SingularInfo){
        saveOrReleaseSingular(singularInfo = singularInfo, status = SingularConstants.SAVED.name)
    }

    private fun saveOrReleaseSingular(singularInfo: SingularInfo, status: String){
        if (singularInfo.imageUrlList.isEmpty()){
            releasePageState = releasePageState.copy(success = false, error = true, errorInfo = "Image url list should not be empty")
        }else if (singularInfo.content.isBlank()){
            releasePageState = releasePageState.copy(success = false, error = true, errorInfo = "Singular content should not be empty")
        }else if (singularInfo.title.isBlank()){
            releasePageState = releasePageState.copy(success = false, error = true, errorInfo = "Singular title should not be empty")
        }else {
            // 如果所有的检查都通过了，就进行上传、创建操作
            val imageUrlList = singularInfo.imageUrlList
            viewModelScope.launch {
                val uploadImagesRes = uploadImages(imagesUrlList = imageUrlList)
                if (uploadImagesRes.status != HttpConstants.SUCCESS || uploadImagesRes.data.isNullOrEmpty()){
                    // 如果失败
                    releasePageState = releasePageState.copy(success = false, error = true, errorInfo = "Upload Image Failed!")
                }else{
                    // 如果成功
                    // 获取返回结果中的图片路径
                    val imagesPathList = uploadImagesRes.data
                    val createRes = createSingular(singularInfo, imagesPathList, status)
                    if (createRes.status == HttpConstants.SUCCESS){
                        releasePageState = releasePageState.copy(success = true, error = false,successInfo = "Create Singular Success!")
                    }
                }
            }
        }
    }

    // create singular using the image path
    private suspend fun createSingular(singularInfo: SingularInfo, imagesPathList: List<String>, status: String): RetrofitResponseBody<Singular> {
        val singularDTO = SingularDTO(content = singularInfo.content, status = status, title = singularInfo.title, category = singularInfo.category, imagesUrl = imagesPathList)
        return singularRepository.createSingular(singularDTO = singularDTO)
    }

    // upload images to server
    private suspend fun uploadImages(imagesUrlList: List<String>): RetrofitResponseBody<List<String>>{
        val imageFileList = mutableListOf<MultipartBody.Part>()
        imagesUrlList.forEach{url ->
            val file = File(url)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val imageFile = MultipartBody.Part.createFormData("image", filename = file.name, requestFile)
            imageFileList.add(imageFile)
        }
        return singularRepository.uploadImageList(multipartFileList = imageFileList)
    }
}

sealed class ReleasePageAction{
    class ReleaseSingular(val singularInfo: SingularInfo): ReleasePageAction()
    class SaveSingular(val singularInfo: SingularInfo): ReleasePageAction()
}

data class ReleasePageState(
    val singularInfo: SingularInfo? = null,
    val success: Boolean = false,
    val successInfo: String = "",
    val error: Boolean = false,
    val errorInfo: String = ""
)

data class SingularInfo(
    val content: String,
    val title: String,
    val category: List<String> = listOf(),
    val imageUrlList: List<String>
)