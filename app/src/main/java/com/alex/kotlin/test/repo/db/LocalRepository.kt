package com.alex.kotlin.test.repo.db

import com.alex.kotlin.test.model.Articles
import com.alex.kotlin.test.repo.db.table.NewsList
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmList


object LocalRepository : LocalDataSource {

    override fun setNews(newsList: List<Articles>) {
        Realm.getDefaultInstance().use({ realm ->
            realm.executeTransaction({
                val newsListObj = NewsList()
                val _newsList = RealmList<Articles>()
                _newsList.addAll(newsList)
                newsListObj.articles = _newsList
                realm.insert(newsListObj)
            })
        })
    }

    override fun getNews(): Flowable<List<Articles>> {
        var articles = Realm.getDefaultInstance()
                .where(NewsList::class.java)
                .findFirst()
                ?.articles

        if(articles == null) articles = RealmList<Articles>()
        return Flowable.just(articles)
    }
}