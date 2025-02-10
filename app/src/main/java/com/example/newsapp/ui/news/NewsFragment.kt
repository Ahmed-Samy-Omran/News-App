package com.example.newsapp.ui.news

import   android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.api.RetrofitBuilder
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.data.model.SourcesItem

import com.example.newsapp.ui.categories.Category
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    companion object {
        private const val CATEGORY_KEY = "category"

        fun getInstance(category: Category): NewsFragment {
            return NewsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CATEGORY_KEY, category)
                }
            }
        }
    }

    private lateinit var category: Category

    private val viewModel: NewsViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getParcelable(CATEGORY_KEY)
            ?: throw IllegalArgumentException("Category is required")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private val adapter = NewsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        observeViewModel()
        viewModel.getNewsSources(category)
    }

    private fun initView(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        tabLayout = view.findViewById(R.id.tab_layout)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
    }

    private fun showTabs(sources: List<SourcesItem?>?) {
        sources?.forEach { item ->
            val tab = tabLayout.newTab().apply {
                tag = item
                text = item?.name
            }
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val source = tab?.tag as? SourcesItem ?: return
                viewModel.loadNews(source)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = tab?.tag as? SourcesItem ?: return
                viewModel.loadNews(source)
            }
        })
        tabLayout.getTabAt(0)?.select()
    }

    private fun observeViewModel() {
        viewModel.newsSourcesLiveData.observe(viewLifecycleOwner) { sources ->
            showTabs(sources)
        }

        viewModel.newsArticlesLiveData.observe(viewLifecycleOwner) { articles ->
            adapter.submitList(articles)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                    progressBar.isVisible = isLoading
                }
            }
        }
    }}