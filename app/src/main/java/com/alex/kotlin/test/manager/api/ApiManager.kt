package com.alex.kotlin.test.manager.api

import com.alex.kotlin.test.api.Api
import com.alex.kotlin.test.api.ApiSettings.KEY
import com.alex.kotlin.test.api.RetrofitCreator.initRetrofit
import com.alex.kotlin.test.api.RetrofitCreator.initServices
import com.alex.kotlin.test.db.ArticlesEntity
import com.alex.kotlin.test.util.wrapArticles
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers


object ApiManager : IApi{

    lateinit var api: Api

    init {
        initServices(initRetrofit())
    }

    fun getNews(country: String, category: String): Flowable<List<ArticlesEntity>> {
        return api.getNews(country, category, KEY)
                .map({ it.articles })
                .map { wrapArticles(it) }
                .observeOn(AndroidSchedulers.mainThread())
    }
}
