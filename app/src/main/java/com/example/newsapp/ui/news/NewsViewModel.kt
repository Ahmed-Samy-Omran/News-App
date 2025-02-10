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

//    ViewModel should not manage Views (RecyclerView, TabLayout, ProgressBar).
//ViewModel should not depend on Context
//ViewModels should not hold UI references (like View, RecyclerView, or Context).

//@HiltViewModel
class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _newsSourcesLiveData = MutableLiveData<List<SourcesItem?>>()
    private val _newsArticlesLiveData = MutableLiveData<List<ArticlesItem?>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>() // New for error handling

    val newsSourcesLiveData: LiveData<List<SourcesItem?>> get() = _newsSourcesLiveData
    val newsArticlesLiveData: LiveData<List<ArticlesItem?>> get() = _newsArticlesLiveData
    val isLoading: LiveData<Boolean> get() = _isLoading
    val errorMessage: LiveData<String> get() = _errorMessage // Observed in UI

    fun loadNews(source: SourcesItem) {
        _newsArticlesLiveData.value = emptyList()
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getNews(Constants.apiKey, source.id ?: "")
                }
                _newsArticlesLiveData.value = response.articles ?: emptyList() // ✅ Runs on Main Thread
            } catch (e: Exception) {
                _errorMessage.value = "Error loading news: ${e.message}"
                Log.e("loadNews", "Error: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getNewsSources(category: Category) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getNewsSources(Constants.apiKey, category.id)
                }
                _newsSourcesLiveData.value = response.sources ?: emptyList()

                // Load news for the first source (if available)
                response.sources?.firstOrNull()?.let { loadNews(it) }
            } catch (e: Exception) {
                _errorMessage.value = "Error loading sources: ${e.message}"
                Log.e("getNewsSources", "Error: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
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