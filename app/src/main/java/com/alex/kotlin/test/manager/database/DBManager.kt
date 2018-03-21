package com.alex.kotlin.test.manager.database

import android.content.Context
import com.alex.kotlin.test.db.ArticlesEntity
import com.alex.kotlin.test.db.DbWorkerThread
import com.alex.kotlin.test.db.NewsDataBase
import io.reactivex.Flowable


class DBManager : IDataBase {

    private var mDb: NewsDataBase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread

    override fun initWithContext(context: Context) {
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
        mDb = NewsDataBase.getInstance(context)
    }

    override fun getNews(): Flowable<List<ArticlesEntity>>? {
        return mDb?.newsDataDao()?.getAll()
    }

    fun insertNewsInDb(articles: List<ArticlesEntity>) {
        val task = Runnable {
            for (article in articles) {
                mDb?.newsDataDao()?.insert(article)
            }
        }
        mDbWorkerThread.postTask(task)
    }
}