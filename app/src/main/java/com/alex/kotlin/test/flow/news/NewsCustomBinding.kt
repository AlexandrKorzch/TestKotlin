package com.alex.kotlin.test.flow.news

import android.databinding.BindingAdapter
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alex.kotlin.test.adapter.ArticlesAdapter
import com.alex.kotlin.test.model.Articles
import com.alex.kotlin.test.util.ScrollChildSwipeRefreshLayout
import com.alex.kotlin.test.util.formatDate
import com.squareup.picasso.Picasso


@BindingAdapter("bind:items")
fun setItems(recyclerView: RecyclerView, articles: ObservableList<Articles>) {
    val adapter = recyclerView.adapter as ArticlesAdapter
    adapter.setArticles(articles)
}

@BindingAdapter("bind:formattedDate")
fun setFormattedDate(textView: TextView, date: String) {
    textView.text = formatDate(date)
}

@BindingAdapter("bind:imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    if(url != null){
        Picasso.with(imageView.context)
                .load(url)
                .into(imageView)
    }else{
        imageView.visibility = View.GONE
    }
}

@BindingAdapter("android:onRefresh")
fun ScrollChildSwipeRefreshLayout.setSwipeRefreshLayoutOnRefreshListener(
        viewModel: NewsViewModel) {
    setOnRefreshListener { viewModel.onRefresh() }
}