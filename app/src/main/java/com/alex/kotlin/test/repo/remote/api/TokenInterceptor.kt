package com.alex.kotlin.test.repo.remote.api

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    val token = "TOKEN"

    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


        val original = chain.request()

//        return originalRequest.newBuilder()
//                .header("accept-language", language)
//                .method(originalRequest.method(), body)
//                .build()



        return chain.proceed(original)

//        return originalRequest.newBuilder()
//                .header("Authorization", token)
//                .method(originalRequest.method(), body)
//                .build()

    }
}