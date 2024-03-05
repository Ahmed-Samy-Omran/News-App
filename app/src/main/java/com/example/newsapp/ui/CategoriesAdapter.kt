package com.example.newsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.google.android.material.card.MaterialCardView

class CategoriesAdapter(val categories:List<Catagory>):RecyclerView.Adapter<CategoriesAdapter.ViewHolder> (){

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val title:TextView=itemView.findViewById(R.id.title)
        val image:ImageView=itemView.findViewById(R.id.image)
        val parent:MaterialCardView=itemView.findViewById(R.id.parent)
        val child:ConstraintLayout=itemView.findViewById(R.id.child)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(
                if (viewType==isLeftSided)
                    R.layout.item_catagory_left_side
                else
                    R.layout.item_catagory_right_side



                ,parent,false)
        return ViewHolder(view)
    }
        val isLeftSided=10
        val isRightSided=20
    override fun getItemViewType(position: Int): Int {
        if (position%2==0){
            return isLeftSided
        }
        else
        {
            return isRightSided
        }
    }

    var onItemClickListener:OnItemClickListener?=null
    interface OnItemClickListener{
        fun onItemClick(pos:Int,item:Catagory)
    }

    override fun getItemCount(): Int =categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cat=categories[position]
        holder.title.setText(cat.titleId)
        holder.image.setImageResource(cat.imageId)
        holder.parent.setCardBackgroundColor(holder.parent.context?.getColor(cat.backgroundColor)?:0)
        //in case onItemClickListener not null
        onItemClickListener?.let {

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position,cat)
        }
        }
    }
}