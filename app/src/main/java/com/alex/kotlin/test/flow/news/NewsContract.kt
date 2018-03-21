package com.alex.kotlin.test.flow.news

import com.alex.kotlin.test.base.BaseMvpPresenter
import com.alex.kotlin.test.base.BaseMvpView
import com.alex.kotlin.test.db.ArticlesEntity


object NewsContract {

    interface View : BaseMvpView {
        fun showNews(articles : List<ArticlesEntity>)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun loadNews()
    }
}