// Correct the package if necessary
package com.example.stockwiz_prototype.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stockwiz_prototype.R
import com.example.stockwiz_prototype.model.NewsItem

class NewsAdapter(private val newsItems: List<NewsItem.Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.news_title)
        val description: TextView = view.findViewById(R.id.news_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = newsItems[position]
        holder.title.text = article.title
        holder.description.text = article.description ?: "No description available."
    }

    override fun getItemCount() = newsItems.size
}
