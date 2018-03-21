package com.alex.kotlin.test.api

import com.alex.kotlin.test.manager.api.ApiManager
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitCreator{

    fun initRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build()
                chain.proceed(request)
            })
            addInterceptor(interceptor)
        }

        return Retrofit.Builder()
                .baseUrl(ApiSettings.SERVER)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(createGsonConverter())
                .client(client.build())
                .build()
    }

    fun initServices(retrofit: Retrofit) {
        ApiManager.api = retrofit.create(Api::class.java)
    }

    private fun createGsonConverter(): GsonConverterFactory {
        val builder = GsonBuilder()
        return GsonConverterFactory.create(builder.create())
    }
}