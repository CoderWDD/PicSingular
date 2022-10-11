package com.example.picsingular.ui.home.picbed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.picsingular.common.paging.CommonPager
import com.example.picsingular.common.utils.retrofit.RetrofitResponseBody
import com.example.picsingular.repository.PicBedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PicBedViewModel @Inject constructor(private val picBedRepository: PicBedRepository) : ViewModel() {
    private val pageDataList by lazy {
        CommonPager { page, size ->
            picBedRepository.getPicBedImagesList(page = page, size = size)
        }
    }

    var picBedPageState by mutableStateOf( PicBedPageState(pageDataList = pageDataList))
        private set

    fun intentHandler(action: PicBedPageAction){
        when (action){
            is PicBedPageAction.UploadImage -> uploadImage(action.imageUrl)
        }
    }

    private fun uploadImage(imageUrl: String){
        val builder = MultipartBody.Builder()
        val file = File(imageUrl)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody = builder.addFormDataPart("multipartFiles", filename = file.name, requestFile).build()
        viewModelScope.launch {
            picBedRepository.uploadImageToPicBed(multipartFiles = multipartBody).collect{res ->
                picBedPageState = picBedPageState.copy(uploadRes = res)
            }
        }
    }
}

data class PicBedPageState(
    val pageDataList: Flow<PagingData<String>>,
    val uploadRes: RetrofitResponseBody<List<String>>? = null
)

sealed class PicBedPageAction{
    class UploadImage(val imageUrl: String): PicBedPageAction()
}