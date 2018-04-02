package com.alex.kotlin.test.repo

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.alex.kotlin.test.global.Const.BUSINES
import com.alex.kotlin.test.global.Const.COUNTRY
import com.alex.kotlin.test.model.Articles
import com.alex.kotlin.test.repo.db.LocalDataSource
import com.alex.kotlin.test.repo.remote.RemoteDataSource
import com.alex.kotlin.test.repo.socket.SocketDataSource
import com.alex.kotlin.test.repo.sp.SpDataSource
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable


class Repository(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val spDataSource: SpDataSource,
        private val socketDataSource: SocketDataSource
) : DataSource, LifecycleObserver {

    val disposables = CompositeDisposable()

    fun getNews(): Flowable<List<Articles>>{
        return localDataSource.getNews()
                .flatMap {
                    if(it.size == 0) {
                        newsRequest(COUNTRY, BUSINES)
                    }else{
                        Flowable.just(it)
                                .doOnNext({disposables.add(newsRequest(COUNTRY, BUSINES).subscribe())})
                    }
                }
    }

    private fun newsRequest(country: String, category: String) : Flowable<List<Articles>> {
        return remoteDataSource.getNews(country, category)
                .doOnNext {localDataSource.setNews(it) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun clear(){
        disposables.dispose()
        disposables.clear()
    }

    companion object {
        private var INSTANCE: Repository? = null
        @JvmStatic fun getInstance(remoteDataSource: RemoteDataSource,
                                   localDataSource: LocalDataSource,
                                   spDataSource: SpDataSource,
                                   socketDataSource: SocketDataSource) =
                INSTANCE ?: synchronized(Repository::class.java) {
                    INSTANCE ?: Repository(remoteDataSource,
                            localDataSource, spDataSource, socketDataSource)
                            .also { INSTANCE = it }
                }
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}