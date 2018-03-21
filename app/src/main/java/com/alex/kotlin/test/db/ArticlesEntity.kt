package com.alex.kotlin.test.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "articles")
class ArticlesEntity(@PrimaryKey(autoGenerate = true) var id: Long?,
                     @ColumnInfo(name = "publishedAt") val publishedAt: String?,
                     @ColumnInfo(name = "author") val author: String?,
                     @ColumnInfo(name = "urlToImage") val urlToImage: String?,
                     @ColumnInfo(name = "title") val title: String?,
                     @ColumnInfo(name = "name") val name: String,
                     @ColumnInfo(name = "description") val description: String?,
                     @ColumnInfo(name = "url") val url: String?)
