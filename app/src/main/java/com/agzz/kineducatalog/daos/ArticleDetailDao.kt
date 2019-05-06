package com.agzz.kineducatalog.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.agzz.kineducatalog.entities.ArticleDetailData

@Dao
interface ArticleDetailDao {

    @Query("SELECT * from ArticleDetailData where id = :id")
    fun getArticleDetailData(id:Int): LiveData<ArticleDetailData>

    @Insert(onConflict = REPLACE)
    fun insertArticleDetailData(articleDetailData: ArticleDetailData)
}