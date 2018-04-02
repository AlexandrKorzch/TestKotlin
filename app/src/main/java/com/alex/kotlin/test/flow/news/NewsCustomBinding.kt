package com.alex.kotlin.test.flow.news

import android.databinding.BindingAdapter
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import com.alex.kotlin.test.adapter.ArticlesAdapter
import com.alex.kotlin.test.model.Articles
import com.alex.kotlin.test.util.ScrollChildSwipeRefreshLayout


@BindingAdapter("bind:items")
fun setItems(recyclerView: RecyclerView, articles: ObservableList<Articles>) {
    val adapter = recyclerView.adapter as ArticlesAdapter
    adapter.setArticles(articles)
}

@BindingAdapter("android:onRefresh")
fun ScrollChildSwipeRefreshLayout.setSwipeRefreshLayoutOnRefreshListener(
        viewModel: NewsViewModel) {
    setOnRefreshListener { viewModel.onRefresh() }
}