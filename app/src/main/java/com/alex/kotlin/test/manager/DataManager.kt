package com.alex.kotlin.test.manager

import android.content.Context
import android.util.Log
import com.alex.kotlin.test.db.ArticlesEntity
import com.alex.kotlin.test.global.Const
import com.alex.kotlin.test.manager.api.ApiManager
import com.alex.kotlin.test.manager.api.IApi
import com.alex.kotlin.test.manager.database.DBManager
import com.alex.kotlin.test.manager.database.IDataBase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class DataManager : IApi, IDataBase {

    private lateinit var dataBaseManager : DBManager
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun initWithContext(context: Context) {
        dataBaseManager = DBManager()
        dataBaseManager.initWithContext(context)
    }

    override fun getNews(): Flowable<List<ArticlesEntity>>? {
        return dataBaseManager.getNews()?.observeOn(AndroidSchedulers.mainThread())
    }

    fun requestNews() {
        compositeDisposable.add(
                ApiManager.getNews(Const.COUNTRY, Const.BUSINES)
                        .subscribe(
                                { dataBaseManager.insertNewsInDb(it) },
                                { showError(it) })
        )
    }

    fun clear(){
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

    private fun showError(throwable: Throwable) {
        Log.d("TAG", "ERROR - " + throwable.message)
    }

}