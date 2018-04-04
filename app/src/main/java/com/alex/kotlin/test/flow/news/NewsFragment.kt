package com.alex.kotlin.test.flow.news

import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alex.kotlin.test.R
import com.alex.kotlin.test.adapter.ArticlesAdapter
import com.alex.kotlin.test.databinding.FragmentNewsBinding


class NewsFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private var adapter: ArticlesAdapter? = null

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = (activity as NewsActivity).obtainViewModel()
        this.lifecycle.addObserver(viewModel)
        this.lifecycle.addObserver(viewModel.repository)
        viewDataBinding = FragmentNewsBinding.inflate(inflater, container, false)
                .apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        viewModel.getNews()
        setupRefreshLayout()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = ArticlesAdapter(viewModel)
            viewDataBinding.rvArticles.adapter = adapter
            viewDataBinding.rvArticles.layoutManager = LinearLayoutManager(context)
        } else {
            Log.w(TAG, "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupRefreshLayout() {
        viewDataBinding.refreshLayout.run {
            setColorSchemeColors(
                    ContextCompat.getColor(activity, R.color.colorPrimary),
                    ContextCompat.getColor(activity, R.color.colorAccent),
                    ContextCompat.getColor(activity, R.color.colorPrimaryDark)
            )
            scrollUpChild = viewDataBinding.rvArticles
        }
    }
}



