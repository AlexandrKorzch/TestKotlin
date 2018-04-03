package com.alex.kotlin.test.repo.remote

import com.alex.kotlin.test.model.Articles
import com.alex.kotlin.test.repo.remote.api.Api
import com.alex.kotlin.test.repo.remote.api.ApiSettings.KEY
import com.alex.kotlin.test.repo.remote.api.RetrofitCreator.initRetrofit
import com.alex.kotlin.test.repo.remote.api.RetrofitCreator.initServices
import io.reactivex.Flowable


object RemoteRepository : RemoteDataSource {

    lateinit var api: Api

    init {
        initServices(initRetrofit())
    }

    override fun getNews(country: String, category: String): Flowable<List<Articles>> {
        return api.getNews(country, category, KEY)
                .map({ it.articles })
    }
}
