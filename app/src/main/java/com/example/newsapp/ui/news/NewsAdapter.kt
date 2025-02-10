package com.example.newsapp.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.model.ArticlesItem

class NewsAdapter : ListAdapter<ArticlesItem, NewsAdapter.ViewHolder>(NewsDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val dateTime: TextView = itemView.findViewById(R.id.datetime)
        private val author: TextView = itemView.findViewById(R.id.author)

        fun bind(item: ArticlesItem) {
            title.text = item.title ?: "No Title"
            author.text = item.author ?: "Unknown Author"
            dateTime.text = item.publishedAt ?: "No Date"

            Glide.with(itemView)
                .load(item.urlToImage)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) // `getItem()` is provided by `ListAdapter`
        holder.bind(item)
    }
}

// 🏆 Optimized `DiffUtil` for better performance
class NewsDiffCallback : DiffUtil.ItemCallback<ArticlesItem>() {
    override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
        return oldItem.url == newItem.url // Assuming the `url` is unique for each article
    }

    override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
        return oldItem == newItem // Checks if the entire object is unchanged
    }
}