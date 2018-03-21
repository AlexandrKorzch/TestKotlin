package com.alex.kotlin.test.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.alex.kotlin.test.R
import com.alex.kotlin.test.flow.news.NewsFragment
import com.alex.kotlin.test.interfaces.FragmentShow
import com.alex.kotlin.test.menu.MenuManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), FragmentShow {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        MenuManager().initDrawer(this, this, drawer_layout, toolbar, nav_view)
    }

    override fun showNews() = setFragment(NewsFragment.newInstance())

    override fun showPush() {
        showToast("not implemented")
    }

    override fun showThreads() {
        showToast("not implemented")
    }

    override fun showMap() {
        showToast("not implemented")
    }

    override fun showCustomView() {
        showToast("not implemented")
    }

    override fun logOut() {
        showToast("not implemented")
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment, "rageComicList")
                .commit()
    }

    private fun showToast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
