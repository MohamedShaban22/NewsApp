package com.example.newsapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.Settings
import com.example.newsapp.databinding.ActivityHomeActiviteBinding
import com.example.newsapp.ui.home.news.NewsFragment

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityHomeActiviteBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var Title:TextView
    lateinit var categoriesFragment:Categories

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeActiviteBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        drawerLayout = viewBinding.drawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer)
        viewBinding.icShowDrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        Title = viewBinding.title

        // Initialize categoriesFragment
         categoriesFragment = Categories()
        setCategoriesName()
        pushFragment(categoriesFragment)

        viewBinding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_categories -> {
                    Title.text = "News App"
                    viewBinding.icSearch.visibility=View.GONE
                    viewBinding.searchView.visibility=View.GONE
                    pushFragment(categoriesFragment)
                }

                R.id.nav_settings -> {
                    Title.text = getString(R.string.settings)
                    val settingsFragment = Settings()
                    viewBinding.icSearch.visibility=View.GONE
                    viewBinding.searchView.visibility=View.GONE
                    pushFragment(settingsFragment)
                }
            }

            viewBinding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        iconSearchOnclick()
        searchApi()

    }

    private fun searchApi() {
        viewBinding.searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val fragment = supportFragmentManager.findFragmentById(R.id.frame_layout) as? NewsFragment
                fragment?.updateSearchQuery(newText)
                return true
            }

        })
    }

    private fun iconSearchOnclick() {
        viewBinding.icSearch.setOnClickListener{
            viewBinding.title.visibility=View.GONE
            viewBinding.icShowDrawer.visibility=View.GONE
            viewBinding.searchView.isVisible=true
            viewBinding.icSearch.visibility=View.GONE

        }

        viewBinding.searchView.setOnCloseListener {
            viewBinding.title.visibility=View.VISIBLE
            viewBinding.icShowDrawer.visibility=View.VISIBLE
            viewBinding.searchView.visibility=View.GONE
            viewBinding.icSearch.visibility=View.VISIBLE
            return@setOnCloseListener true
        }
    }

    private fun setCategoriesName() {
        categoriesFragment.onCategoriesClickSport=object :Categories.OnCategoriesClickListener{
            override fun onCategoriesClick() {
                viewBinding.icSearch.isVisible=true
                viewBinding.title.text= getString(R.string.sports)
            }
        }

        categoriesFragment.onCategoriesClickEntertainment=object :Categories.OnCategoriesClickListener{
            override fun onCategoriesClick() {
                viewBinding.icSearch.isVisible=true
                viewBinding.title.text=getString(R.string.entertainment)
            }

        }
        categoriesFragment.onCategoriesClickHealth=object :Categories.OnCategoriesClickListener{
            override fun onCategoriesClick() {
                viewBinding.icSearch.isVisible=true
                viewBinding.title.text=getString(R.string.health)
            }

        }
        categoriesFragment.onCategoriesClickTechnology=object :Categories.OnCategoriesClickListener{
            override fun onCategoriesClick() {
                viewBinding.icSearch.isVisible=true
                viewBinding.title.text=getString(R.string.technology)
            }

        }
        categoriesFragment.onCategoriesClickScience=object :Categories.OnCategoriesClickListener{
            override fun onCategoriesClick() {
                viewBinding.icSearch.isVisible=true
                viewBinding.title.text=getString(R.string.science)
            }

        }
        categoriesFragment.onCategoriesClickBusiness=object :Categories.OnCategoriesClickListener{
            override fun onCategoriesClick() {
                viewBinding.icSearch.isVisible=true
                viewBinding.title.text=getString(R.string.business)
            }

        }

    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout,fragment).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
//01006821470