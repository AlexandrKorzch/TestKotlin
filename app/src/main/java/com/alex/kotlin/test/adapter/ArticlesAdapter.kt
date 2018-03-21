package com.alex.kotlin.test.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alex.kotlin.test.R
import com.alex.kotlin.test.db.ArticlesEntity
import com.alex.kotlin.test.util.formatDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_layout.view.*


class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    private var mArticles: List<ArticlesEntity> = ArrayList()
    private var clickCalBack: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.article_layout, parent, false)
        return ViewHolder(view, clickCalBack)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mArticles[position])
    }

    override fun getItemCount(): Int = mArticles.size

    fun setArticles(articles: List<ArticlesEntity>) {
        mArticles = articles
        notifyDataSetChanged()
    }

    fun setClickCallBack(clickCalBack: ItemClick?) {
        this.clickCalBack = clickCalBack
    }

    class ViewHolder(itemView: View, var clickCalBack: ItemClick?) : RecyclerView.ViewHolder(itemView) {

        fun bind(article: ArticlesEntity) = with(itemView) {

            tv_title.text = article.title
            tv_author.text = article.author
            tv_name.text = article.name
            tv_description.text = article.description
            tv_date.text = formatDate(article.publishedAt)

            Picasso.with(context)
                    .load(article.urlToImage)
                    .into(iv_picture)

            itemView.setOnClickListener { clickCalBack?.openUrl(article.url) }
        }
    }

    interface ItemClick {
        fun openUrl(url: String?)
    }
}
