package com.alex.kotlin.test.flow.news

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.alex.kotlin.test.R
import com.alex.kotlin.test.adapter.ArticlesAdapter
import com.alex.kotlin.test.base.BaseMvpFragment
import com.alex.kotlin.test.db.ArticlesEntity
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment :
        BaseMvpFragment<NewsContract.View, NewsContract.Presenter>(),
        NewsContract.View, ArticlesAdapter.ItemClick {

    override var presenter: NewsContract.Presenter = NewsPresenter()
    private var mAdapter: ArticlesAdapter? = null

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_news

    override fun onResume() {
        super.onResume()
        showProgress()
        initRecyclerView()
        presenter.loadNews()
    }

    private fun initRecyclerView() {
        rv_articles.setItemAnimator(DefaultItemAnimator())
        val mLinearLayoutManager = LinearLayoutManager(context)
        rv_articles.setLayoutManager(mLinearLayoutManager)
        mAdapter = ArticlesAdapter()
        rv_articles.setAdapter(mAdapter)
    }

    override fun showNews(articles: List<ArticlesEntity>) {
        hideProgress()
        mAdapter?.setClickCallBack(this);
        mAdapter?.setArticles(articles);
    }

    override fun openUrl(url: String?) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}


