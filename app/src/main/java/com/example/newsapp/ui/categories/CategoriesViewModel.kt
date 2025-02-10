package com.example.newsapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.R
import dagger.hilt.android.lifecycle.HiltViewModel


class CategoriesViewModel:ViewModel() {

    private val _categories =MutableLiveData<List<Category>>()
    val categories :LiveData<List<Category>> get() = _categories

    // The init block is used to initialize data as soon as the ViewModel is created
    init {
        loadCategories() // Called when ViewModel is first created
    }

    private fun loadCategories() {
        _categories.value =listOf(
            Category("sports", R.drawable.sports, R.string.sports, R.color.red),
            Category("technology", R.drawable.politics, R.string.technology, R.color.blue),
            Category("health", R.drawable.health, R.string.health, R.color.pink),
            Category("business", R.drawable.bussines, R.string.business, R.color.dark_orange),
            Category("general", R.drawable.environment, R.string.general, R.color.light_blue),
            Category("science", R.drawable.science, R.string.science, R.color.yellow)
        )
    }
}