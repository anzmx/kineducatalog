package com.agzz.kineducatalog.activities

import android.os.Bundle

import com.agzz.kineducatalog.R
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.appcompat.app.AppCompatActivity

import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.agzz.kineducatalog.adapters.CatalogPagerAdapter
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_family -> {
                    //TODO Menu items
                    return true
                }
                R.id.navigation_plan -> {
                    //TODO Menu items
                    return true
                }
                R.id.navigation_catalog -> {
                    //TODO Menu catalog
                    return true
                }
                R.id.navigation_progress -> {
                    //TODO Menu items
                    return true
                }
                R.id.navigation_milestones -> {
                    //TODO Menu items
                    return true
                }

            }
            return false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navView.selectedItemId = R.id.navigation_catalog
        supportActionBar?.hide()
        val fragmentAdapter = CatalogPagerAdapter(supportFragmentManager)
        val viewpagerCatalog = findViewById<ViewPager>(R.id.pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        viewpagerCatalog.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewpagerCatalog)
    }

}
