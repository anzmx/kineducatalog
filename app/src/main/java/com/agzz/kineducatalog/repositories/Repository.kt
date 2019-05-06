package com.agzz.kineducatalog.repositories

import com.agzz.kineducatalog.entities.*
import com.agzz.kineducatalog.network.DataResponse
import com.agzz.kineducatalog.network.networkCall
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.http.Path
import retrofit2.http.Query


object Repository {


    fun getActivities(skillID: String, babyId: String) = networkCall<ActivitiesReposResponse, ActivityData> {
        client = KineduAPI.kineduActivitiesService.getActivities(skillID,babyId)
    }
    fun getArticles(skillID: String, babyId: String) = networkCall<ArticleReposResponse, ArticleData> {
        client = KineduAPI.kineduArticlesService.getArticles(skillID,babyId)
    }

    fun getArticleDetail(articleId: String) = networkCall<ArticleDetailReposResponse, ArticleDetailData> {
        client = KineduAPI.kineduArticleDetailService.getArticleDetail(articleId)
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

    var kineduActivitiesService = retrofit.create<KineduActivitiesService>(KineduActivitiesService::class.java)
    var kineduArticlesService = retrofit.create<KineduArticlesService>(KineduArticlesService::class.java)
    var kineduArticleDetailService = retrofit.create<KineduArticleDetailService>(KineduArticleDetailService::class.java)



    interface KineduActivitiesService {
        @GET("catalogue/activities")
        fun getActivities(@Query("skill_id") skillId: String, @Query("baby_id") babyId: String): Deferred<Response<ActivitiesReposResponse>>
    }
    interface KineduArticlesService {
        @GET("catalogue/articles")
        fun getArticles(@Query("skill_id") skillId: String, @Query("baby_id") babyId: String): Deferred<Response<ArticleReposResponse>>
    }

    interface KineduArticleDetailService {
        @GET("articles/{article_id}")
        fun getArticleDetail(@Path("article_id", encoded = false) articleId: String): Deferred<Response<ArticleDetailReposResponse>>
    }
}