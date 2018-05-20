package com.alex.kotlin.test.repo.remote.api

import okhttp3.Interceptor
import okhttp3.Response



class LanguageInterceptor : Interceptor {

    val language = "LANGUAGE"

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

//        return originalRequest.newBuilder()
//                .header("accept-language", language)
//                .method(originalRequest.method(), body)
//                .build()


        return chain.proceed(original)
    }
}