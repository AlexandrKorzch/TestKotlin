package com.alex.kotlin.test.flow.news

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.util.Log
import com.alex.kotlin.test.global.Const.BUSINESS
import com.alex.kotlin.test.global.Const.COUNTRY
import com.alex.kotlin.test.model.Articles
import com.alex.kotlin.test.repo.Repository
import com.alex.kotlin.test.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer


class NewsViewModel(context: Application, val repository: Repository)
    : AndroidViewModel(context), LifecycleObserver {

    private val disposables = CompositeDisposable()
    val openUrlEvent = SingleLiveEvent<String>()
    val items = ObservableArrayList<Articles?>()
    val dataLoading = ObservableBoolean(false)

    fun getNews() {
        dataLoading.set(true)
        disposables.add(
                repository.newsRequest(COUNTRY, BUSINESS).subscribe(

                        Consumer {
                            Log.d("TAG", "NewsViewModel $it")

                        }

                ))
    }

    fun onRefresh() {
        clear()
        getNews()
    }

    fun openUrl(url: String){
        openUrlEvent.value = url
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun clear() {
        disposables.dispose()
        disposables.clear()
    }
}
