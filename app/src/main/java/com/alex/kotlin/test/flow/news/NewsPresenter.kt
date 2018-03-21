package com.alex.kotlin.test.flow.news

import com.alex.kotlin.test.base.BaseMvpPresenterImpl


class NewsPresenter : BaseMvpPresenterImpl<NewsContract.View>(), NewsContract.Presenter {

    override fun loadNews() {

        addDisposable(
                dataManager.getNews()?.subscribe(
                        { mView?.showNews(it) },
                        { mView?.showError(it.toString()) })
        )

         dataManager.requestNews()
    }
}