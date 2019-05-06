package com.agzz.kineducatalog.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.agzz.kineducatalog.daos.ActivitiesDao
import com.agzz.kineducatalog.daos.ArticlesDao
import com.agzz.kineducatalog.entities.Activity
import com.agzz.kineducatalog.entities.Article

@Database(entities = [Activity::class, Article::class], version = 1)
abstract class KineduDatabase : RoomDatabase() {

    abstract fun activitiesDao(): ActivitiesDao
    abstract fun articlesDao(): ArticlesDao

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