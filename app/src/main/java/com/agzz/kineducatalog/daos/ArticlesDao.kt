package com.agzz.kineducatalog.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.agzz.kineducatalog.entities.Article

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM Article")
    fun getAll(): List<Article>

    @Insert(onConflict = REPLACE)
    fun insert(article: Article)
}