package com.alex.kotlin.test.repo.sp

import android.content.Context
import com.alex.kotlin.test.App
import java.util.*


object SharedPrefRepo :SpDataSource {

    private val NAME = "sharedPrefs"
    private val THEME = "mTheme"

    private val mCachedValues = HashSet<Any>()
    private var mTheme: CachedValue<Int>

    init {
        CachedValue.sp = App.applicationContext().getSharedPreferences(NAME, Context.MODE_PRIVATE)

        mTheme = CachedValue<Int>(THEME, -1, Int.javaClass)
        mCachedValues.add(mTheme)
    }


    override fun setTheme(theme: Int) {
        mTheme.setValue(theme)
    }

    override fun getTheme(): Int? {
        return mTheme.getValue()
    }
}