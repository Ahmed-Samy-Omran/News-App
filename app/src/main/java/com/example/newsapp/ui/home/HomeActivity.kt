package com.example.newsapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.ui.categories.CategoriesFragment
import com.example.newsapp.ui.categories.Category
import com.example.newsapp.ui.news.NewsFragment
import com.example.newsapp.ui.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

// create obj from categoriesFragment to can push fragment when i start the program
    private val categoriesFragment = CategoriesFragment()  // Fixed spelling & made it private
    private val settingsFragment = SettingsFragment()


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerIcon: ImageView
    private lateinit var settings: View
    private lateinit var categories: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setupListeners()
        // Push CategoriesFragment on startup
        pushFragment(categoriesFragment)

        // Handle category click inside CategoriesFragment
        categoriesFragment.onCategoryClickListener = object : CategoriesFragment.OnCategoryClickListener {
            override fun onCategoryClick(category: Category) {
                pushFragment(NewsFragment.getInstance(category), addToBackStack = true)
            }
        }
    }

    private fun setupListeners() {
        drawerIcon.setOnClickListener {
            drawerLayout.open()
        }

        settings.setOnClickListener {
            pushFragment(SettingsFragment())

        }
        categories.setOnClickListener {
            pushFragment(categoriesFragment)
        }
    }

    private fun initViews() {
        setContentView(R.layout.activity_home)
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerIcon = findViewById(R.id.drawer_icon)
        settings = findViewById(R.id.settings)
        categories = findViewById(R.id.categories)
    }


    private fun pushFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        // Avoid replacing if the fragment is already visible
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == fragment) return

        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        //Apply the Changes
        transaction.commit()
       // This ensures a clean UI by hiding the drawer once the user navigates.
        drawerLayout.close()
    }
}