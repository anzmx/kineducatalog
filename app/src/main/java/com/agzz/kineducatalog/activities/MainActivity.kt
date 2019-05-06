package com.agzz.kineducatalog.activities
import android.os.Bundle
import android.util.Log

import com.agzz.kineducatalog.R
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.appcompat.app.AppCompatActivity

import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
        val filterSpinner: Spinner = findViewById(R.id.ageSpinner)
        val ageOptions = arrayOf("ALL MONTHS","0-1 MONTHS","2 MONTHS","3 MONTHS","4 MONTHS","5 MONTHS","6 MONTHS","7 MONTHS","8 MONTHS","9 MONTHS","10 MONTHS","11 MONTHS","12 MONTHS","13 MONTHS","14 MONTHS","15 MONTHS","16 MONTHS")
        val adapter = ArrayAdapter(
                this, // Context
                R.layout.spinner_layout, // Layout
                ageOptions // Array
        )
        adapter.setDropDownViewResource(R.layout.dropdown_item_layout)
        filterSpinner.adapter = adapter

        // Set an on item selected listener for spinner object
        filterSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                Log.d("Filter: ", ageOptions[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }


        viewpagerCatalog.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewpagerCatalog)
    }

}
