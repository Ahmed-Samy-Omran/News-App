package com.example.newsapp.ui.news

import   android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.SourcesItem

import com.example.newsapp.ui.categories.Catagory
import com.google.android.material.tabs.TabLayout


class NewsFragment:Fragment() {

    companion object{
        fun getInstance(category: Catagory): NewsFragment {
            val fragment= NewsFragment()
           fragment.catagory=category
            return fragment

        }

    }

    lateinit var catagory: Catagory
    private val viewModel: NewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news,container,false)
    }

    lateinit var progressBar:ProgressBar
    lateinit var tabLayout: TabLayout
    lateinit var recyclerView: RecyclerView
    val adapter= NewsAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getNewsSources(catagory) { sources ->
            showTabs(sources)
        }
    }

    private fun initView(){
        progressBar=requireView().findViewById(R.id.progress_bar)
        tabLayout=requireView().findViewById(R.id.tab_layout)
        recyclerView=requireView().findViewById(R.id.recycler_view)
        recyclerView.adapter=adapter
        viewModel.initView(progressBar, tabLayout, recyclerView, requireContext())    }

    private fun showTabs(sources: List<SourcesItem?>?) {
        sources?.forEach { item ->
            val tab = tabLayout.newTab()
            tab.tag = item
            tab.text = item?.name
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    viewModel.loadNews(source)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    viewModel.loadNews(source)
                }
            }
        )
        tabLayout.getTabAt(0)?.select()
    }
}