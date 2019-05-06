package com.agzz.kineducatalog.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.agzz.kineducatalog.entities.ArticleDetailData
import com.agzz.kineducatalog.network.Resource
import com.agzz.kineducatalog.repositories.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ArticleDetailViewModel(var app:Application) : AndroidViewModel(app){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : Repository = Repository(app)


    val articleDetailLiveData = MutableLiveData<ArticleDetailData>()

    fun fetchArticleDetail(articleId: Int, lifecycleOwner: LifecycleOwner){
        scope.launch(Dispatchers.Main) {
            repository.getArticleDetail(articleId,lifecycleOwner).observe(lifecycleOwner, Observer { it ->
                it?.let { articleDetailLiveData.postValue(it) } })
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}