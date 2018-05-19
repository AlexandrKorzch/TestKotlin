package com.alex.kotlin.test.repo.remote

import com.alex.kotlin.test.model.Articles
import io.reactivex.Flowable


interface RemoteDataSource {

    fun getNews(country: String, category: String): Flowable<Object>
}