package com.agzz.kineducatalog.room

import androidx.room.TypeConverter
import com.agzz.kineducatalog.entities.Activity
import com.agzz.kineducatalog.entities.Article
import com.agzz.kineducatalog.entities.ArticleX
import com.agzz.kineducatalog.entities.RelatedItems
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.util.*


class KineduTypeConverters {
    var gson = Gson()

    @TypeConverter
    fun stringToActivityList(data: String?): List<Activity> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Activity>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun ActivityListToString(someObjects: List<Activity>): String {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToArticleList(data: String?): List<Article> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Article>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun ArticleListToString(someObjects: List<Article>): String {
        return gson.toJson(someObjects)
    }


    @TypeConverter
    fun ArticleDetailToString(someObjects: ArticleX): String {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToArticleDetail(data: String?): ArticleX {

        return gson.fromJson(data,ArticleX::class.java)
    }

    @TypeConverter
    fun RelatedItemsToString(someObjects: RelatedItems): String {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToRelatedItems(data: String?): RelatedItems {

        return gson.fromJson(data,RelatedItems::class.java)
    }
}

