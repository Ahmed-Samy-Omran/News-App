package com.example.newsapp.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.google.android.material.card.MaterialCardView

class CategoriesAdapter(
    private val onItemClick: (Category) -> Unit
) : ListAdapter<Category, CategoriesAdapter.ViewHolder>(CategoryDiffCallback()) {

    companion object {
        private const val LEFT_SIDED = 10
        private const val RIGHT_SIDED = 20
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val image: ImageView = view.findViewById(R.id.image)
        val parent: MaterialCardView = view.findViewById(R.id.parent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = if (viewType == LEFT_SIDED) {
            R.layout.item_catagory_left_side
        } else {
            R.layout.item_catagory_right_side
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) LEFT_SIDED else RIGHT_SIDED
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)

        holder.apply {
            title.setText(category.titleId)
            image.setImageResource(category.imageId)
            parent.setCardBackgroundColor(parent.context.getColor(category.backgroundColor))
            itemView.setOnClickListener { onItemClick(category) }
        }
    }
}

// DiffUtil to optimize item updates
class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}