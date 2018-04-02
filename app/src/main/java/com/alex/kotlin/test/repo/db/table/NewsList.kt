package com.alex.kotlin.test.repo.db.table

import com.alex.kotlin.test.model.Articles
import io.realm.RealmList
import io.realm.RealmObject


open class NewsList : RealmObject() {
    var articles =  RealmList<Articles>()
}