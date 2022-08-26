package com.example.picsingular.common.utils.retrofit

import android.util.Log
import com.example.picsingular.common.constants.HttpConstants
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

// 相当于所有retrofit网络请求的代理，所有的请求都从这里发出，并在这里处理完返回结果
object ApiCallHandler {
    suspend fun <T> apiCall(networkCall: suspend () -> RetrofitResponseBody<T>): RetrofitResponseBody<T> {
        return withContext(Dispatchers.IO) {
            val res: RetrofitResponseBody<T>
            try{
                res = networkCall()
            }catch(e: Throwable){
                return@withContext castExceptionToRetrofitResponseBody(e)
            }
            return@withContext res
        }
    }

    private fun <T> castExceptionToRetrofitResponseBody(e: Throwable): RetrofitResponseBody<T> {
        Log.e("wgw", "castExceptionToRetrofitResponseBody: $e", )
        return  when (e) {
            is HttpException -> {
                val body = Gson().fromJson(e.response()?.errorBody()?.string(), RetrofitResponseBody::class.java)
                    ?: return RetrofitResponseBody(status = HttpConstants.NET_ERROR, message = "请求出错(${e.message})", null)
                RetrofitResponseBody(status = body.status, message = body.message, data = null)
            }
            is SocketTimeoutException -> {
                RetrofitResponseBody(status = HttpConstants.CONNECT_TIME_OUT, message = "网络连接超时，请稍后重试", data = null)
            }
            is IOException -> {
                RetrofitResponseBody(status = HttpConstants.BAD_REQUEST, message = "数据格式出错，请稍后重试", data = null)
            }
            else -> {
                RetrofitResponseBody(status = HttpConstants.NET_ERROR, message = "请求出错(${e.message})", data = null)
            }
        }
    }
}