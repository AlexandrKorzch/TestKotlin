package com.alex.kotlin.test.repo.sp


interface SpDataSource {
    fun setTheme(theme: Int)
    fun getTheme(): Int?
}