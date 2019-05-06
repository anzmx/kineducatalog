package com.agzz.kineducatalog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.agzz.kineducatalog.entities.Article
import com.agzz.kineducatalog.entities.ArticleData

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM ArticleData")
    fun getArticlesData(): LiveData<ArticleData>

    @Insert(onConflict = REPLACE)
    fun insertArticlesData(articleData: ArticleData)

    @Query("SELECT * FROM Article")
    fun getAll(): List<Article>

    @Insert(onConflict = REPLACE)
    fun insert(article: Article)

    @Insert(onConflict = REPLACE)
    fun insertAll(articles: List<Article>)
}