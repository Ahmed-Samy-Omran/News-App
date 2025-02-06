package com.example.newsapp.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.ArticlesItem

class NewsAdapter(var items:List<ArticlesItem?>?=null):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val title:TextView=itemView.findViewById(R.id.title)
        val image:ImageView=itemView.findViewById(R.id.image)
        val dateTime:TextView=itemView.findViewById(R.id.datetime)
        val author:TextView=itemView.findViewById(R.id.author)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0 //in case it be null return 0
    }

    fun changeData(data:List<ArticlesItem?>?){
        items=data;
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=items!![position]
        holder.title.setText(item?.title)
        holder.author.setText(item?.author)
        holder.dateTime.setText(item?.publishedAt)
        Glide.with(holder.itemView)
            .load(item?.urlToImage)
            .into(holder.image)
    }
}