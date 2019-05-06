package com.agzz.kineducatalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.agzz.kineducatalog.R
import com.agzz.kineducatalog.entities.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.articles_list_row.view.*


class ArticlesAdapter(private val fragment: Fragment) : RecyclerView.Adapter<ArticlesViewHolder>(){

   var articlesList:List<Article> = emptyList()

    //number of item
    override fun getItemCount(): Int {
        return articlesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        //create view
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.articles_list_row, parent, false)
        return ArticlesViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val article = articlesList[position]
        holder.view.article_item_name.text = article.name
        holder.view.article_item_short_desc.text = article.short_description
        Glide.with(fragment)
                .load(article.picture)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.view.article_item_picture)

    }

    fun setData(newData: List<Article>) {
        this.articlesList = newData
        notifyDataSetChanged()
    }
}
class ArticlesViewHolder(val view: View): RecyclerView.ViewHolder(view)
