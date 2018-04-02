package com.alex.kotlin.test.repo.remote.api


import com.alex.kotlin.test.repo.remote.api.ApiSettings.API_KEY
import com.alex.kotlin.test.repo.remote.api.ApiSettings.CATEGORY
import com.alex.kotlin.test.repo.remote.api.ApiSettings.COUNTRY
import com.alex.kotlin.test.repo.remote.api.ApiSettings.GET_NEWS
import com.alex.kotlin.test.model.Data
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    @GET(GET_NEWS)
    fun getNews(@Query(COUNTRY) country: String,
                @Query(CATEGORY) category: String,
                @Query(API_KEY) key : String): Flowable<Data>
}