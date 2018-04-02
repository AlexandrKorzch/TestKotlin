package com.alex.kotlin.test.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alex.kotlin.test.R
import com.alex.kotlin.test.flow.news.NewsViewModel
import com.alex.kotlin.test.model.Articles
import com.alex.kotlin.test.util.formatDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_layout.view.*


class ArticlesAdapter(private val newsViewModel: NewsViewModel
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    private var mArticles: List<Articles> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.article_layout, parent, false)
        return ViewHolder(view, newsViewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mArticles[position])
    }

    override fun getItemCount(): Int = mArticles.size

    fun setArticles(articles: List<Articles>) {
        mArticles = articles
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, var newsViewModel: NewsViewModel?) : RecyclerView.ViewHolder(itemView) {

        fun bind(article: Articles) = with(itemView) {

            with(article) {

                tv_title.text = title
                tv_author.text = author
                tv_name.text = source?.name
                tv_description.text = description
                tv_date.text = formatDate(publishedAt)

                Picasso.with(context)
                        .load(article.urlToImage)
                        .into(iv_picture)
            }

            itemView.setOnClickListener {
                newsViewModel?.openUrlEvent?.value = article.url
            }
        }
    }
}
