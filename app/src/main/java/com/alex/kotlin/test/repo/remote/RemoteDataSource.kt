package com.alex.kotlin.test.repo.remote

import com.alex.kotlin.test.model.Articles
import io.reactivex.Flowable
import io.realm.RealmList


interface RemoteDataSource {

    fun getNews(country: String, category: String): Flowable<List<Articles>>
}