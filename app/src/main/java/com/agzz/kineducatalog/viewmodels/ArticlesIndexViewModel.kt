package com.agzz.kineducatalog.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.agzz.kineducatalog.entities.ActivityData
import com.agzz.kineducatalog.entities.ArticleData
import com.agzz.kineducatalog.network.Resource
import com.agzz.kineducatalog.repositories.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ArticlesIndexViewModel(var app:Application) : AndroidViewModel(app){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : Repository = Repository(app)


    val articlesLiveData = MutableLiveData<ArticleData>()

    fun fetchArticles(skillId:String, babyId: String, lifecycleOwner: LifecycleOwner){
        scope.launch(Dispatchers.Main) {
            repository.getArticles(skillId,babyId,lifecycleOwner).observe(lifecycleOwner, Observer { it ->
                it?.let {articlesLiveData.postValue(it)  } })
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}