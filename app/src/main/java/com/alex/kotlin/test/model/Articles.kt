package com.alex.kotlin.test.model

import io.realm.RealmObject


open class Articles : RealmObject(){
    var id: Long? = null
    var publishedAt: String? = null
    var author: String? = null
    var urlToImage: String? = null
    var title: String? = null
    var source: Source? = null
    var description: String? = null
    var url: String? = null
}