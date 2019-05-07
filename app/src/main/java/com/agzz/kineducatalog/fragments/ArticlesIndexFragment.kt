package com.agzz.kineducatalog.fragments

import android.content.Intent
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
import com.agzz.kineducatalog.activities.ArticleDetailActivity
import com.agzz.kineducatalog.adapters.ArticlesAdapter
import com.agzz.kineducatalog.entities.Article
import com.agzz.kineducatalog.viewmodels.ArticlesIndexViewModel
import com.agzz.kineducatalog.viewmodels.FilterValueViewModel
import com.bumptech.glide.Glide


class ArticlesIndexFragment : Fragment(){

    private lateinit var articlesViewModel: ArticlesIndexViewModel
    private lateinit var articlesRecyclerView : RecyclerView
    private lateinit var articlesAdapter : ArticlesAdapter
    private lateinit var progressBar : ProgressBar
    private var filteredArticleList:List<Article>? = null
    private var originalArticleList:List<Article>? = null

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
        articlesAdapter = ArticlesAdapter(Glide.with(this))
        articlesRecyclerView.adapter = articlesAdapter
        articlesViewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            originalArticleList = it.articles
            filteredArticleList = originalArticleList
            articlesAdapter.setData(originalArticleList!!)
            progressBar.visibility = View.GONE

        })
        articlesViewModel.fetchArticles("5","2064732",viewLifecycleOwner)
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        articlesRecyclerView.addItemDecoration(decoration)

        var filterViewModel: FilterValueViewModel = ViewModelProviders.of(this.activity!!).get(FilterValueViewModel::class.java)
        filterViewModel.selectedAge.observe(this, Observer {
            Log.d("ArticlesFragment: ", it.toString())
            filterArticles(it)
        })

        articlesAdapter.onItemClick = { article ->
            val intent = Intent(this.activity, ArticleDetailActivity::class.java)
            intent.putExtra("ArticleId", article.id)
            startActivity(intent)
        }

        return rootView
    }
    fun filterArticles(selectedAge:Int){
        if (selectedAge!= 0) {
            filteredArticleList = originalArticleList!!.filter { selectedAge in it.min_age..it.max_age }
            articlesAdapter.setData(filteredArticleList!!)
        }
        else{
            if (originalArticleList!=null){
                articlesAdapter.setData(originalArticleList!!)
            }
        }
    }

}
