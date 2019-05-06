package com.agzz.kineducatalog.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.agzz.kineducatalog.daos.ActivitiesDao
import com.agzz.kineducatalog.daos.ArticleDetailDao
import com.agzz.kineducatalog.daos.ArticlesDao
import com.agzz.kineducatalog.entities.*

@Database(entities = [Activity::class, Article::class, ActivityData::class, ArticleData::class, ArticleDetailData::class], version = 1)
@TypeConverters(KineduTypeConverters::class)

abstract class KineduDatabase : RoomDatabase() {

    abstract fun activitiesDao(): ActivitiesDao
    abstract fun articlesDao(): ArticlesDao
    abstract fun articleDetailDao(): ArticleDetailDao

    companion object {
        private var INSTANCE: KineduDatabase? = null

        fun getInstance(context: Context):KineduDatabase? {
            if (INSTANCE == null) {
                synchronized(KineduDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            KineduDatabase::class.java, "KineduDb.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}