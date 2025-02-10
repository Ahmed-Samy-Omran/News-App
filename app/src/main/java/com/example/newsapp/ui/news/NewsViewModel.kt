package com.example.newsapp.ui.news

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Constants
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.data.model.SourcesItem
import com.example.newsapp.ui.categories.Category
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//@HiltViewModel
class NewsViewModel(private val repository: NewsRepository): ViewModel() {

    private lateinit var progressBar: View
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var context: Context

    // Internal mutable LiveData
    private val _newsSourcesLiveData = MutableLiveData<List<SourcesItem?>>()
    private val _newsArticlesLiveData = MutableLiveData<List<ArticlesItem?>>()
    private val _isLoading = MutableLiveData<Boolean>()

    // Exposing immutable LiveData
    val newsSourcesLiveData: LiveData<List<SourcesItem?>> get() = _newsSourcesLiveData
    val newsArticlesLiveData: LiveData<List<ArticlesItem?>> get() = _newsArticlesLiveData
    val isLoading: LiveData<Boolean> get() = _isLoading

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


    fun loadNews(source: SourcesItem) {
        adapter.changeData(null)
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getNews(Constants.apiKey, source.id ?: "")
                _newsArticlesLiveData.postValue(response.articles ?: emptyList())
            } catch (e: Exception) {
                showError("Error loading news")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getNewsSources(catagory: Category) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getNewsSources(Constants.apiKey, catagory.id)
                if (response.sources.isNullOrEmpty()) {
                    Log.e("getNewsSources", "No sources found for category: ${catagory.id}")
                } else {
                    withContext(Dispatchers.Main) {
                        _newsSourcesLiveData.postValue(response.sources ?: emptyList())
                        Log.d("getNewsSources", "Sources loaded: ${response.sources.size}")
                        // Load news for the first source
                        response.sources.firstOrNull()?.let { loadNews(it) }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("getNewsSources", "Error loading news sources: ${e.message}", e)
                    showError("Error loading news sources: ${e.message}")
                }
            } finally {
                withContext(Dispatchers.Main) {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    private fun showError(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}