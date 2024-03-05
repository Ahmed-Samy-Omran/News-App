package com.example.newsapp.ui

import   android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Constants
import com.example.newsapp.R
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.model.SourceResponse
import com.example.newsapp.model.SourcesItem
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment:Fragment() {

    companion object{
        fun getInstance(category:Catagory):NewsFragment{
            val fragment=NewsFragment()
           fragment.catagory=category
            return fragment

        }

    }

    lateinit var catagory: Catagory
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
    val adapter=NewsAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getNewsSources()
    }

    private fun initView(){
        progressBar=requireView().findViewById(R.id.progress_bar)
        tabLayout=requireView().findViewById(R.id.tab_layout)
        recyclerView=requireView().findViewById(R.id.recycler_view)
        recyclerView.adapter=adapter
    }

    private fun getNewsSources() {
        ApiManager.getApis().getNewsSources(Constants.apiKey,catagory.id)
            .enqueue(object :Callback<SourceResponse>{
            override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                progressBar.isVisible=false

            }

            override fun onResponse(
                call: Call<SourceResponse>,
                response: Response<SourceResponse>
            ) {
                progressBar.isVisible=false
                showTabs(response.body()?.sources)
                Log.e("response",response.body().toString())
            }
        })
    }

    private fun showTabs(sources: List<SourcesItem?>?) {
    sources?.forEach { item->
        val tab=tabLayout.newTab()
        tab.setTag(item)
        tab.setText(item?.name)
        tabLayout.addTab(tab)
    }
        tabLayout.addOnTabSelectedListener(
            object :TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source= tab?.tag as SourcesItem
                    loadNews(source)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source= tab?.tag as SourcesItem
                    loadNews(source)
                }

            }
        )
        tabLayout.getTabAt(0)?.select()

    }
    fun loadNews(source:SourcesItem){
        adapter.changeData(null)
        progressBar.isVisible=true
    ApiManager.getApis().getNews(Constants.apiKey,source.id?:"").enqueue(
        object :Callback<NewsResponse>{
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                progressBar.isVisible=false
                adapter.changeData(response.body()?.articles)

            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(requireContext(),"Error loading news",Toast.LENGTH_LONG).show()
                progressBar.isVisible=false

            }

        }
    )
    }
}