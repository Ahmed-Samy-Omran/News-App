package com.example.newsapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R

//@AndroidEntryPoint
class CategoriesFragment:Fragment() {

    // i create onViewCreated to work on it
    private lateinit var recyclerView: RecyclerView
    private val viewModel:CategoriesViewModel by viewModels()

    private val adapter by lazy {
        CategoriesAdapter { category ->
            onCategoryClickListener?.onCategoryClick(category)
        }
    }
    var onCategoryClickListener: OnCategoryClickListener?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catagories,container,false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }
    }

    private fun initRecyclerView() {
        recyclerView = requireView().findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
    }





    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }

}
