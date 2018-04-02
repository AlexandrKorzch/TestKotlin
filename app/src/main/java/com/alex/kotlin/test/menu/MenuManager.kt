package com.alex.kotlin.test.menu

import android.app.Activity
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import com.alex.kotlin.test.R


class MenuManager {

    fun initDrawer(activity: Activity, showCallback: FragmentShow, drawer_layout: DrawerLayout, toolbar: Toolbar, nav_view: NavigationView) {
        val toggle = ActionBarDrawerToggle(activity, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener({
            when (it.itemId) {
                R.id.nav_news -> showCallback.showNews()
                R.id.nav_push -> showCallback.showPush()
                R.id.nav_threads -> showCallback.showThreads()
                R.id.nav_map -> showCallback.showMap()
                R.id.nav_custom_view -> showCallback.showCustomView()
                R.id.nav_logout -> showCallback.logOut()
            }
            drawer_layout.closeDrawers()
            return@setNavigationItemSelectedListener true
        })
    }
}