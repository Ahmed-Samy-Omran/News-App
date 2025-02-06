package com.example.newsapp.ui.news

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Constants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.model.SourceResponse
import com.example.newsapp.model.SourcesItem
import com.example.newsapp.ui.categories.Catagory
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel: ViewModel() {

    private lateinit var progressBar: View
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var context: Context

    fun initView(
        progressBar: View,
        tabLayout: TabLayout,
        recyclerView: RecyclerView,
        context: Context
    ){
        this.progressBar=progressBar
        this.tabLayout=tabLayout
        this.recyclerView=recyclerView
        this.adapter = recyclerView.adapter as NewsAdapter
        this.context = context
    }
    fun loadNews(source: SourcesItem){
        adapter.changeData(null)
        progressBar.isVisible=true
        ApiManager.getApis().getNews(Constants.apiKey,source.id?:"").enqueue(
            object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    progressBar.isVisible=false
                    adapter.changeData(response.body()?.articles)

                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Toast.makeText(context,"Error loading news", Toast.LENGTH_LONG).show()
                    progressBar.isVisible=false

                }

            }
        )
    }

    fun getNewsSources(catagory: Catagory, callback: (List<SourcesItem?>?) -> Unit) {
        progressBar.isVisible = true
        ApiManager.getApis().getNewsSources(Constants.apiKey, catagory.id)
            .enqueue(object : Callback<SourceResponse> {
                override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                    progressBar.isVisible = false
                }

                override fun onResponse(
                    call: Call<SourceResponse>,
                    response: Response<SourceResponse>
                ) {
                    progressBar.isVisible = false
                    callback(response.body()?.sources)
                    Log.e("response", response.body().toString())
                }
            })
    }



}