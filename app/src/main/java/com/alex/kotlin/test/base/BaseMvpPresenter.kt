package com.alex.kotlin.test.base


interface BaseMvpPresenter <in V : BaseMvpView> {

    fun attachView(view: V)

    fun detachView()
}