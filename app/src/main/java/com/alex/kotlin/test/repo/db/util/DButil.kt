package com.alex.kotlin.test.repo.db.util

import com.alex.kotlin.test.model.Articles
import com.alex.kotlin.test.repo.db.table.NewsList


fun updateArticlesList(newsList: List<Articles>, newsListObj: NewsList): NewsList {
    if (newsListObj.articles.size != 0) {
        for (article in newsList) {
            var contains = false
            for (dbArticle in newsListObj.articles) {
                if (dbArticle.title.equals(article.title)) {
                    contains = true
                    break
                }
            }
            if(!contains){
                newsListObj.articles.add(0, article)
            }
        }
    }else {
        newsListObj.articles.addAll(newsList)
    }
    return newsListObj
}