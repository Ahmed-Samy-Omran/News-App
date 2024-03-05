package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R

class CatagoriesFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catagories,container,false)
    }

//make categories
    val categories= listOf(
        Catagory("sports",R.drawable.sports,R.string.sports,R.color.red),
        Catagory("technology",R.drawable.politics,R.string.technology,R.color.blue) ,
        Catagory("health",R.drawable.health,R.string.health,R.color.pink),
        Catagory("bussines",R.drawable.bussines,R.string.business,R.color.dark_orange),
        Catagory("general",R.drawable.environment,R.string.general,R.color.light_blue),
        Catagory("science",R.drawable.science,R.string.science,R.color.yellow),
    )
    // i create onViewCreated to work on it
    lateinit var recyclerView: RecyclerView
    val adapter=CategoriesAdapter(categories)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
       recyclerView=requireView().findViewById(R.id.recycler_view)
        recyclerView.adapter=adapter
        adapter.onItemClickListener= object :CategoriesAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, item: Catagory) {
                onCategoryClickListener?.onCategoryClick(catagory = item)
                // i take call back and connect it to another call back to return item
            }
        }
    }

    var onCategoryClickListener:OnCategoryClickListener?=null
    interface OnCategoryClickListener{
        fun onCategoryClick(catagory: Catagory) // it need category i clicked it only i dont need postion
    }

}
