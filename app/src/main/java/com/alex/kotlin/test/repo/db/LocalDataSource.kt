package com.alex.kotlin.test.repo.db

import com.alex.kotlin.test.model.Articles
import io.reactivex.Flowable

interface LocalDataSource {
    fun setNews(it: List<Articles>)
    fun getNews(): Flowable<List<Articles>>
}