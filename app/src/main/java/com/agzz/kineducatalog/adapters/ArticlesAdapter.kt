package com.agzz.kineducatalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agzz.kineducatalog.R
import com.agzz.kineducatalog.entities.Article
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.articles_list_row.view.*


class ArticlesAdapter(private val glide: RequestManager) : RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>(){
    var onItemClick: ((Article) -> Unit)? = null
    var articlesList:List<Article> = emptyList()

    override fun getItemCount(): Int {
        return articlesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.articles_list_row, parent, false)
        return ArticlesViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val article = articlesList[position]
        holder.itemView.article_item_name.text = article.name
        holder.itemView.article_item_short_desc.text = article.short_description
        glide.load(article.picture)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.article_item_picture)
    }

    fun setData(newData: List<Article>) {
        this.articlesList = newData
        notifyDataSetChanged()
    }
    inner class ArticlesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                onItemClick?.invoke(articlesList[adapterPosition])
            }
        }
    }
}

