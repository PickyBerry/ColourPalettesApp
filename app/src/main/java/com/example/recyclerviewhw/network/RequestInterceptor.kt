package com.example.recyclerviewhw.network

import okhttp3.Interceptor
import okhttp3.Response

//Basic request interceptor
object RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }
}
