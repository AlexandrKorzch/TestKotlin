package com.alex.kotlin.test.manager.database

import android.content.Context
import com.alex.kotlin.test.db.ArticlesEntity
import io.reactivex.Flowable

interface IDataBase {
    fun initWithContext(context: Context)
    fun getNews(): Flowable<List<ArticlesEntity>>?
}