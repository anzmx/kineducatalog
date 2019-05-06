package com.agzz.kineducatalog.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.agzz.kineducatalog.entities.ArticleDetailData
import com.agzz.kineducatalog.network.Resource
import com.agzz.kineducatalog.repositories.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ArticleDetailViewModel : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : Repository = Repository


    val articleDetailLiveData = MutableLiveData<Resource<ArticleDetailData>>()

    fun fetchArticleDetail(articleId: String, lifecycleOwner: LifecycleOwner){
        scope.launch(Dispatchers.Main) {
            repository.getArticleDetail(articleId).observe(lifecycleOwner, Observer {  articleDetailLiveData.postValue(it) })
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}