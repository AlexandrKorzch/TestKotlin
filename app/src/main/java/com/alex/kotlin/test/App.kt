package com.alex.kotlin.test

import android.app.Application
import android.content.Context
import io.realm.Realm


class App : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}