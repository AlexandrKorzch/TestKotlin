package com.alex.kotlin.test.util

import com.alex.kotlin.test.db.ArticlesEntity
import com.alex.kotlin.test.model.Articles

fun wrapArticles(articles: List<Articles>): List<ArticlesEntity>{
    val entities = ArrayList<ArticlesEntity>(articles.size)
    for(article in articles){
        val entity = ArticlesEntity(
                article.id,
                article.publishedAt,
                article.author,
                article.urlToImage,
                article.title,
                article.source.name,
                article.description,
                article.url
        )
        entities.add(entity)
    }
    return entities
}