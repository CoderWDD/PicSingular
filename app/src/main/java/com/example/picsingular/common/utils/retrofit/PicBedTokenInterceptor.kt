package com.example.picsingular.common.utils.retrofit

import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import com.example.picsingular.common.constants.TokenConstants
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.util.Locale

class PicBedTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val startTime = SystemClock.elapsedRealtime()
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()

        Log.e("picBed", "intercept: ${TokenConstants.PIC_BED_TOKEN}" )
        if (TokenConstants.PIC_BED_TOKEN.isNotEmpty()){
            newRequestBuilder.addHeader("Authorization", "Bearer " + TokenConstants.PIC_BED_TOKEN)
        }
        val newRequest = newRequestBuilder.build()

        val response = chain.proceed(newRequest)


        val endTime = SystemClock.elapsedRealtime()
        val duration = endTime - startTime

        //url
        val url = newRequest.url.toString()
        Log.e("retrofit","----------Request Start----------")
        Log.e("retrofit","" + newRequest.method + " " + url)


        //param
        val requestBody = newRequest.body
        val paramString = readRequestParamString(requestBody)
        if (!TextUtils.isEmpty(paramString)) {
            Log.e("retrofit","Params:$paramString")
        }

        //response
        val responseBody = response.body
        var responseString = ""
        if (null != responseBody) {
            responseString = if (isPlainText(responseBody.contentType())) {
                readContent(response)
            } else {
                "other-type=" + responseBody.contentType()
            }
        }
        Log.e("retrofit","Response Body:$responseString")
        Log.e("retrofit","Time:$duration ms")
        Log.e("retrofit","----------Request End----------")


        return response
    }


    private fun readRequestParamString(requestBody: RequestBody?): String {
        val paramString: String
        if (requestBody is MultipartBody) { //判断是否有文件
            val sb = StringBuilder()
            val parts: List<MultipartBody.Part> = requestBody.parts
            var partBody: RequestBody
            var i = 0
            val size = parts.size
            while (i < size) {
                partBody = parts[i].body
                i++
                if (sb.isNotEmpty()) {
                    sb.append(",")
                }
                if (isPlainText(partBody.contentType())) {
                    sb.append(readContent(partBody))
                } else {
                    sb.append("other-param-type=").append(partBody.contentType())
                }
            }
            paramString = sb.toString()
        } else {
            paramString = readContent(requestBody)
        }
        return paramString
    }

    private fun readContent(response: Response?): String {
        if (response == null) {
            return ""
        }
        try {
            return response.peekBody(Long.MAX_VALUE).string()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun readContent(body: RequestBody?): String {
        if (body == null) {
            return ""
        }
        val buffer = Buffer()
        try {
            //小于2m
            if (body.contentLength() <= 2 * 1024 * 1024) {
                body.writeTo(buffer)
            } else {
                return "content is more than 2M"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return buffer.readUtf8()
    }

    private fun isPlainText(mediaType: MediaType?): Boolean {
        if (null != mediaType) {
            var mediaTypeString = mediaType.toString()
            if (!TextUtils.isEmpty(mediaTypeString)) {
                mediaTypeString = mediaTypeString.lowercase(Locale.getDefault())
                if (mediaTypeString.contains("text") || mediaTypeString.contains("application/json")) {
                    return true
                }
            }
        }
        return false
    }
}