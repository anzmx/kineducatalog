package com.agzz.kineducatalog.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agzz.kineducatalog.adapters.ActivitiesAdapter
import com.agzz.kineducatalog.R
import com.agzz.kineducatalog.viewmodels.ActivityViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import com.agzz.kineducatalog.network.Resource
import com.bumptech.glide.Glide


class ActivitiesIndexFragment : Fragment(){

    private lateinit var activitiesViewModel: ActivityViewModel
    private lateinit var activitiesRecyclerView : RecyclerView
    private lateinit var activitiesAdapter : ActivitiesAdapter
    private lateinit var progressBar : ProgressBar

    companion object {
        fun newInstance(): ActivitiesIndexFragment {
            return ActivitiesIndexFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_activities_index, container, false)
        progressBar = rootView.findViewById(R.id.progressBar)
        activitiesViewModel = ViewModelProviders.of(this).get(ActivityViewModel::class.java)
        activitiesRecyclerView = rootView.findViewById(R.id.activities_recyclerview) as RecyclerView
        activitiesRecyclerView.layoutManager = LinearLayoutManager(activity)
        activitiesAdapter = ActivitiesAdapter(Glide.with(this))
        activitiesRecyclerView.adapter = activitiesAdapter
        activitiesViewModel.activitiesLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.LOADING -> {
                    Log.d("MainActivity", "--> Loading activities")
                    progressBar.visibility = View.VISIBLE
                }
                Resource.SUCCESS -> {
                    Log.d("MainActivity", "--> Success! | loaded ${it.data!!.activities.size} activities.")
                    activitiesAdapter.setData(it.data!!.activities)
                    progressBar.visibility = View.GONE

                }
                Resource.ERROR -> {
                    Log.d("MainActivity", "--> Error loading activities!")
                    Toast.makeText(activity,"Error loading activities", Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            }
        })
        activitiesViewModel.fetchActivities("5","2064732",viewLifecycleOwner)
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        activitiesRecyclerView.addItemDecoration(decoration)
        return rootView

    }


}
