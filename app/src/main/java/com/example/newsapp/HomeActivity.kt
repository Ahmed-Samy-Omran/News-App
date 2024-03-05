package com.example.newsapp

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavBackStackEntry
import com.example.newsapp.ui.CatagoriesFragment
import com.example.newsapp.ui.Catagory
import com.example.newsapp.ui.NewsFragment
import com.example.newsapp.ui.SettingsFragment


class HomeActivity : AppCompatActivity() {
// create obj from catagoriesFragment to can push fragment when i start the program
    val catagoriesFragment =CatagoriesFragment();
    val settingsFragment =SettingsFragment()
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerIcon:ImageView
    lateinit var settings:View
    lateinit var categories: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        drawerLayout=findViewById(R.id.drawer_layout)
        drawerIcon=findViewById(R.id.drawer_icon)
        settings=findViewById(R.id.settings)
        categories=findViewById(R.id.categories)
        pushFragment(catagoriesFragment)
        catagoriesFragment.onCategoryClickListener=object :CatagoriesFragment.OnCategoryClickListener{
            override fun onCategoryClick(catagory: Catagory) {
                pushFragment(NewsFragment.getInstance(catagory),true)
            }

        }
        drawerIcon.setOnClickListener {
            drawerLayout.open()
        }

        settings.setOnClickListener {
            pushFragment(SettingsFragment())

        }
        categories.setOnClickListener {
            pushFragment(catagoriesFragment)
        }

    }

    // create fun for push fragment and pass obj from fragment
    fun pushFragment(fragment: Fragment,addToBackStack:Boolean=false){
        val fragTransction=  supportFragmentManager.beginTransaction().
        replace(R.id.fragment_container,fragment)
        if (addToBackStack)     //see if addToBackStack is true and i pass it as parameter then do addToBackStack then commit
            fragTransction.addToBackStack("")
        fragTransction.commit()


        drawerLayout.close()

    }



}

