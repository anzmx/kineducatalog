package com.agzz.kineducatalog.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.agzz.kineducatalog.entities.ArticleData
import com.agzz.kineducatalog.network.Resource
import com.agzz.kineducatalog.repositories.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ArticlesIndexViewModel : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : Repository = Repository


    val articlesLiveData = MutableLiveData<Resource<ArticleData>>()

    fun fetchArticles(skillId:String, babyId: String, lifecycleOwner: LifecycleOwner){
        scope.launch(Dispatchers.Main) {
            repository.getArticles(skillId,babyId).observe(lifecycleOwner, Observer {  articlesLiveData.postValue(it) })
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}