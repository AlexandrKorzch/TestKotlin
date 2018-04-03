package com.alex.kotlin.test.repo.db

import com.alex.kotlin.test.model.Articles
import com.alex.kotlin.test.repo.db.table.NewsList
import com.alex.kotlin.test.repo.db.util.updateArticlesList
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmList


object LocalRepository : LocalDataSource {

    override fun setNews(newsList: List<Articles>) {
        Realm.getDefaultInstance().use({ realm ->
            realm.executeTransaction({
                var newsListObj = Realm.getDefaultInstance()
                        .where(NewsList::class.java)
                        .findFirst() ?: NewsList()

                newsListObj = updateArticlesList(newsList, newsListObj)
                realm.insertOrUpdate(newsListObj)
            })
        })
    }

    override fun getNews(): Flowable<List<Articles>> {
        var articles = Realm.getDefaultInstance()
                .where(NewsList::class.java)
                .findFirst()
                ?.articles
        if (articles == null) articles = RealmList<Articles>()
        return Flowable.just(articles)
    }
}