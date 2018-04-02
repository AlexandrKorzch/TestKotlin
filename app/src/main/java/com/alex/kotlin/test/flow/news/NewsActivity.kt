package com.alex.kotlin.test.flow.news

import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alex.kotlin.test.R
import com.alex.kotlin.test.util.obtainViewModel
import com.alex.kotlin.test.util.replaceFragmentInActivity

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setupViewFragment()
        obtainViewModel().apply {
            openUrlEvent.observe(this@NewsActivity, Observer<String> {
                this@NewsActivity.openUrl(it)
            })
        }
    }

     fun openUrl(url: String?) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame) ?:
        NewsFragment.newInstance().let {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }
    }

    fun obtainViewModel(): NewsViewModel = obtainViewModel(NewsViewModel::class.java)
}
