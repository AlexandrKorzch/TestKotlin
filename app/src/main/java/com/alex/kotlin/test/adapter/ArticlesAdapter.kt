package com.alex.kotlin.test.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alex.kotlin.test.databinding.ArticleLayoutBinding
import com.alex.kotlin.test.flow.news.NewsViewModel
import com.alex.kotlin.test.model.Articles


class ArticlesAdapter(private val newsViewModel: NewsViewModel)
    : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    private var mArticles: List<Articles> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val articleBind = ArticleLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(articleBind, newsViewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mArticles[position])
    }

    override fun getItemCount(): Int = mArticles.size

    fun setArticles(articles: List<Articles>) {
        mArticles = articles
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ArticleLayoutBinding, private var newsViewModel: NewsViewModel?)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Articles) {
            binding.viewModel = newsViewModel
            binding.article = article
        }
    }
}
