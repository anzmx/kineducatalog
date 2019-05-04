package com.agzz.kineducatalog.Activities

import android.os.Bundle

import com.agzz.kineducatalog.R
import com.agzz.kineducatalog.Repositories.ArticleReposResponse
import com.agzz.kineducatalog.Repositories.Repository
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.appcompat.app.AppCompatActivity

import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.agzz.kineducatalog.ViewModels.ActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var activitiesViewModel: ActivityViewModel

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_family -> return@OnNavigationItemSelectedListener true
            R.id.navigation_plan -> return@OnNavigationItemSelectedListener true
            R.id.navigation_catalog -> return@OnNavigationItemSelectedListener true
            R.id.navigation_progress -> return@OnNavigationItemSelectedListener true
            R.id.navigation_milestones -> return@OnNavigationItemSelectedListener true
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navView.selectedItemId = R.id.navigation_catalog
        activitiesViewModel = ViewModelProviders.of(this).get(ActivityViewModel::class.java)
        activitiesViewModel.activitiesLiveData.observe(this, Observer {  })
        activitiesViewModel.fetchActivities()
    }



}
