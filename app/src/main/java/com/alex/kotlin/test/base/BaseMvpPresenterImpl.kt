package com.alex.kotlin.test.base

import android.content.Context
import com.alex.kotlin.test.manager.DataManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class BaseMvpPresenterImpl<V : BaseMvpView> : BaseMvpPresenter<V> {

    protected var mView: V? = null
    protected lateinit var dataManager: DataManager
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        mView = view
        initDataManager(view.getContext())
    }

    override fun detachView() {
        mView = null
        removeSubscribers()
    }

    private fun initDataManager(context: Context){
        dataManager = DataManager()
        dataManager.initWithContext(context)
    }

    protected fun addDisposable(disposable: Disposable?){
        disposable?.let {
            compositeDisposable.add(disposable)
        }
    }

    private fun removeSubscribers() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        dataManager.clear()
    }
}