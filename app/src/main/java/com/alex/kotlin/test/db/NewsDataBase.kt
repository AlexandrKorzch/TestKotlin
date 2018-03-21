package com.alex.kotlin.test.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = arrayOf(ArticlesEntity::class), version = 1)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun newsDataDao(): NewsDao

    companion object {

        @Volatile private var INSTANCE: NewsDataBase? = null

        fun getInstance(context: Context): NewsDataBase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        NewsDataBase::class.java, "NewsDB.db")
                        .build()
    }
}