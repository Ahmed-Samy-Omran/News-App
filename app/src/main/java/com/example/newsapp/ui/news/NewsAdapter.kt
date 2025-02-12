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
import com.example.newsapp.databinding.ItemNewsBinding

class NewsAdapter : ListAdapter<ArticlesItem, NewsAdapter.ViewHolder>(NewsDiffCallback()) {

    class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticlesItem) {
            // Use ViewBinding to access views directly
            binding.title.text = item.title ?: "No Title"
            binding.author.text = item.author ?: "Unknown Author"
            binding.datetime.text = item.publishedAt ?: "No Date"

            Glide.with(itemView)
                .load(item.urlToImage)
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
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