package com.example.picsingular.common.utils.retrofit

import com.example.picsingular.common.constants.TokenConstants
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (TokenConstants.TOKEN.isNotEmpty()){
            val pair = Pair("Authorization","Bearer ${TokenConstants.TOKEN}")
            request.headers.plus(pair)
        }
        return response
    }
}