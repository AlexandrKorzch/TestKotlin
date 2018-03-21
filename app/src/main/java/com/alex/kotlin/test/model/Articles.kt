package com.alex.kotlin.test.model


data class Articles(var id: Long?,
                    val publishedAt: String?,
                    val author: String?,
                    val urlToImage: String?,
                    val title: String?,
                    val source: Source,
                    val description: String?,
                    val url: String?)