package com.agzz.kineducatalog.room

import androidx.room.TypeConverter
import com.agzz.kineducatalog.entities.Activity
import com.agzz.kineducatalog.entities.Article
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
         fun stringToArticleyList(data: String?): List<Article> {
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
      }