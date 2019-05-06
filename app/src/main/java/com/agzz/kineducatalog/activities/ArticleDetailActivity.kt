package com.agzz.kineducatalog.activities

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.agzz.kineducatalog.R
import com.agzz.kineducatalog.network.Resource
import com.agzz.kineducatalog.viewmodels.ArticleDetailViewModel
import com.agzz.kineducatalog.viewmodels.ArticlesIndexViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.articles_list_row.view.*

class ArticleDetailActivity: AppCompatActivity() {

    private lateinit var articleDetailViewModel: ArticleDetailViewModel
    private lateinit var textBody:TextView
    private lateinit var articleImage: ImageView
    private lateinit var articleTitle: TextView
    private lateinit var progressBar : ProgressBar
    private lateinit var backArrow : ImageButton
    private var articleId:Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        val extras = intent.extras
        if (null != extras) {
           articleId = extras.getInt("ArticleId")
        }
        supportActionBar?.hide()
        textBody = findViewById(R.id.article_detail_body)
        articleImage = findViewById(R.id.article_detail_picture)
        progressBar = findViewById(R.id.progressBar)
        articleTitle = findViewById(R.id.article_detail_title)
        backArrow = findViewById(R.id.article_detail_back)
        backArrow.setOnClickListener {
            this.finish()
        }
        articleDetailViewModel = ViewModelProviders.of(this).get(ArticleDetailViewModel::class.java)
        articleDetailViewModel.articleDetailLiveData.observe(this, Observer {
            when(it.status){
                Resource.LOADING -> {
                    Log.d("ArticleDetailActivity", "--> Loading article Detail")
                    progressBar.visibility = View.VISIBLE
                }
                Resource.SUCCESS -> {
                    Log.d("ArticleDetailActivity", "--> Success! | loaded article detail.")
                    Glide.with(this)
                            .load(it.data!!.article.picture)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(articleImage)
                    val htmlbody: Spanned = Html.fromHtml(it!!.data!!.article.body,Html.FROM_HTML_MODE_LEGACY)
                    textBody.text = htmlbody
                    textBody.setLinkTextColor(Color.parseColor("#859BB1"))
                    articleTitle.text = it.data!!.article.title



                    progressBar.visibility = View.GONE

                }
                Resource.ERROR -> {
                    Log.d("ArticleDetailActivity", "--> Error loading article detail!")
                    Toast.makeText(this,"Error loading articles", Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            }
        })
        articleDetailViewModel.fetchArticleDetail(articleId.toString(),this)
    }
}