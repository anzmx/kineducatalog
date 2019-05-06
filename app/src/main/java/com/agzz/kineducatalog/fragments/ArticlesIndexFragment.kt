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
import com.agzz.kineducatalog.R
import androidx.recyclerview.widget.DividerItemDecoration
import com.agzz.kineducatalog.adapters.ArticlesAdapter
import com.agzz.kineducatalog.network.Resource
import com.agzz.kineducatalog.viewmodels.ArticlesIndexViewModel


class ArticlesIndexFragment : Fragment(){

    private lateinit var articlesViewModel: ArticlesIndexViewModel
    private lateinit var articlesRecyclerView : RecyclerView
    private lateinit var articlesAdapter : ArticlesAdapter
    private lateinit var progressBar : ProgressBar

    companion object {
        fun newInstance(): ArticlesIndexFragment {
            return ArticlesIndexFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_articles_index, container, false)
        progressBar = rootView.findViewById(R.id.progressBar)
        articlesViewModel = ViewModelProviders.of(this).get(ArticlesIndexViewModel::class.java)
        articlesRecyclerView = rootView.findViewById(R.id.articles_recyclerview) as RecyclerView
        articlesRecyclerView.layoutManager = LinearLayoutManager(activity)
        articlesAdapter = ArticlesAdapter(this)
        articlesRecyclerView.adapter = articlesAdapter
       articlesViewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.LOADING -> {
                    Log.d("MainActivity", "--> Loading articles")
                    progressBar.visibility = View.VISIBLE
                }
                Resource.SUCCESS -> {
                    Log.d("MainActivity", "--> Success! | loaded ${it.data!!.articles.size} articles.")
                    articlesAdapter.setData(it.data!!.articles)
                    progressBar.visibility = View.GONE

                }
                Resource.ERROR -> {
                    Log.d("MainActivity", "--> Error loading articles!")
                    Toast.makeText(activity,"Error loading articles", Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            }
        })
        articlesViewModel.fetchArticles("5","2064732",viewLifecycleOwner)
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        articlesRecyclerView.addItemDecoration(decoration)

        return rootView
    }


}
