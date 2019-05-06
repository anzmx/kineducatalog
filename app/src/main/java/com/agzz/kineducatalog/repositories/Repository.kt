package com.agzz.kineducatalog.repositories

import android.app.Application
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.room.RoomDatabase
import com.agzz.kineducatalog.daos.ActivitiesDao
import com.agzz.kineducatalog.daos.ArticlesDao
import com.agzz.kineducatalog.entities.*
import com.agzz.kineducatalog.network.DataResponse
import com.agzz.kineducatalog.network.Resource
import com.agzz.kineducatalog.network.awaitResult
import com.agzz.kineducatalog.network.networkCall
import com.agzz.kineducatalog.room.KineduDatabase
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.http.Path
import retrofit2.http.Query


class Repository(application: Application) {
    var application: Application = application
    var localDb: KineduDatabase = KineduDatabase.getInstance(application)!!
    var activitiesDao: ActivitiesDao = localDb.activitiesDao()
    var articlesDao: ArticlesDao = localDb.articlesDao()



    fun verifyAvailableNetwork(): Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun getActivities(skillId:String,babyId:String, lifecycleOwner: LifecycleOwner):LiveData<ActivityData>{
        if (verifyAvailableNetwork()) {
            updateDbActivities(skillId, babyId, lifecycleOwner)
        }
        return getActivitiesDataFromDb()
    }

    fun getArticles(skillId:String,babyId:String, lifecycleOwner: LifecycleOwner):LiveData<ArticleData>{
        if (verifyAvailableNetwork()) {
            updateDbArticles(skillId, babyId, lifecycleOwner)
        }
        return getArticlesDataFromDb()
    }

    fun getActivitiesDataFromDb() = activitiesDao.getActivityData()

    fun getArticlesDataFromDb() = articlesDao.getArticlesData()

    fun getActivitiesFromApi(skillID: String, babyId: String): MutableLiveData<Resource<ActivityData>> =
            networkCall<ActivitiesReposResponse, ActivityData> {
        client = KineduAPI.kineduAPIService.getActivities(skillID,babyId)
    }
    fun getArticlesFromApi(skillID: String, babyId: String): MutableLiveData<Resource<ArticleData>> =
            networkCall<ArticleReposResponse, ArticleData> {
                client = KineduAPI.kineduAPIService.getArticles(skillID,babyId)
            }

    fun getArticleDetail(articleId: String) = networkCall<ArticleDetailReposResponse, ArticleDetailData> {
        client = KineduAPI.kineduAPIService.getArticleDetail(articleId)
    }

    fun updateDbActivities(skillId: String,babyId: String,lifecycleOwner: LifecycleOwner): Unit {
        getActivitiesFromApi(skillId,babyId).observe(lifecycleOwner, Observer {
            when(it.status){
                Resource.LOADING -> {
                    Log.d("Repository", "--> Loading activities to DB")
                }
                Resource.SUCCESS -> {
                    Log.d("Repository", "--> Success! | loaded ${it.data!!.activities.size} activities.")
                    GlobalScope.launch {  activitiesDao.insertActivityData(it.data!!) }

                }
                Resource.ERROR -> {
                    Log.d("Repository", "--> Error loading activities!")
                }
            }
        })
    }

    fun updateDbArticles(skillId: String,babyId: String,lifecycleOwner: LifecycleOwner): Unit {
        getArticlesFromApi(skillId,babyId).observe(lifecycleOwner, Observer {
            when(it.status){
                Resource.LOADING -> {
                    Log.d("Repository", "--> Loading articles to DB")
                }
                Resource.SUCCESS -> {
                    Log.d("Repository", "--> Success! | loaded ${it.data!!.articles.size} articles.")
                    GlobalScope.launch {  articlesDao.insertArticlesData(it.data!!) }

                }
                Resource.ERROR -> {
                    Log.d("Repository", "--> Error loading articles!")
                }
            }
        })
    }
}

data class ActivitiesReposResponse(val data: ActivityData): BaseApiResponse<ActivityData>(), DataResponse<ActivityData> {
    override fun retrieveData(): ActivityData = data
}

data class ArticleReposResponse(val data: ArticleData): BaseApiResponse<ArticleData>(), DataResponse<ArticleData> {
    override fun retrieveData(): ArticleData = data
}

data class ArticleDetailReposResponse(val data: ArticleDetailData): BaseApiResponse<ActivityData>(), DataResponse<ArticleDetailData> {
    override fun retrieveData(): ArticleDetailData = data
}

abstract class BaseApiResponse<T> {
    var total_count: Int = 0
    var incomplete_results: Boolean = false
}

object KineduAPI {
    private fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", "Token token=5105f4358e45f6f98057a654c882b7742c3ac5241c81a706acc48c84f8acde9f8a344993ac42369627ae9f2caf1eed42ff1be9562fe2167c9c80908e76e95c49")
                .build())
    }

    var API_BASE_URL: String = "http://staging.kinedu.com/api/v3/"
    var httpClient = OkHttpClient.Builder().addInterceptor(headersInterceptor())
    var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
    var retrofit = builder
            .client(httpClient.build())
            .build()

    var kineduAPIService = retrofit.create<KineduAPIService>(KineduAPIService::class.java)



    interface KineduAPIService {
        @GET("catalogue/activities")
        fun getActivities(@Query("skill_id") skillId: String, @Query("baby_id") babyId: String): Deferred<Response<ActivitiesReposResponse>>

        @GET("catalogue/articles")
        fun getArticles(@Query("skill_id") skillId: String, @Query("baby_id") babyId: String): Deferred<Response<ArticleReposResponse>>

        @GET("articles/{article_id}")
        fun getArticleDetail(@Path("article_id", encoded = false) articleId: String): Deferred<Response<ArticleDetailReposResponse>>
    }


}