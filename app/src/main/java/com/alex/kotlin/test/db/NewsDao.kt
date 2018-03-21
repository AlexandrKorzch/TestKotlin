package com.alex.kotlin.test.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable


@Dao
interface NewsDao {

    @Query("SELECT * from articles")
    fun getAll(): Flowable<List<ArticlesEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(weatherData: ArticlesEntity)

    @Query("DELETE from articles")
    fun deleteAll()
}