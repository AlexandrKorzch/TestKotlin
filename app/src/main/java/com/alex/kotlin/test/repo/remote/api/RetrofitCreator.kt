package com.alex.kotlin.test.repo.remote.api

import com.alex.kotlin.test.BuildConfig
import com.alex.kotlin.test.repo.remote.RemoteRepository
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitCreator {

    fun initRetrofit(): Retrofit {


        val client = OkHttpClient.Builder().apply {
            networkInterceptors().add(Base64Interceptor("network"))
            networkInterceptors().add(TokenInterceptor())
            networkInterceptors().add(LanguageInterceptor())

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                if (!interceptors().contains(logging)) {
                    addInterceptor(logging)
                }
            }
        }

        return Retrofit.Builder()
                .baseUrl(ApiSettings.SERVER)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(createGsonConverter())
                .client(client.build())
                .build()
    }

    fun initServices(retrofit: Retrofit) {
        RemoteRepository.api = retrofit.create(Api::class.java)
    }

    private fun createGsonConverter(): GsonConverterFactory {
        val builder = GsonBuilder()
        return GsonConverterFactory.create(builder.create())
    }
}