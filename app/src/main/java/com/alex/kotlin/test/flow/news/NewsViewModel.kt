package com.alex.kotlin.test.flow.news

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.util.Log
import com.alex.kotlin.test.model.Articles
import com.alex.kotlin.test.repo.Repository
import com.alex.kotlin.test.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable


class NewsViewModel(context: Application, val repository: Repository)
    : AndroidViewModel(context), LifecycleObserver {

    private val disposables = CompositeDisposable()
    val openUrlEvent = SingleLiveEvent<String>()
    val items = ObservableArrayList<Articles?>()
    val dataLoading = ObservableBoolean(false)

    fun getNews() {
        dataLoading.set(true)
        disposables.add(
                repository.getNews()
                        .subscribe(
                                {
                                    dataLoading.set(false)
                                    with(items) {
                                        clear()
                                        addAll(it)
                                    }
                                },
                                { Log.d("TAG", "ERROR ${it.message}") }))
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
