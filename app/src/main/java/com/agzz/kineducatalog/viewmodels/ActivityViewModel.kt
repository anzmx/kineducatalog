package com.agzz.kineducatalog.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.agzz.kineducatalog.entities.ActivityData
import com.agzz.kineducatalog.repositories.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ActivityViewModel(var app:Application) : AndroidViewModel(app){


    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : Repository = Repository(app)


    val activitiesLiveData = MutableLiveData<ActivityData>()

    fun fetchActivities(skillId:String, babyId: String, lifecycleOwner: LifecycleOwner){
        scope.launch(Dispatchers.Main) {
            repository.getActivities(skillId,babyId, lifecycleOwner).observe(lifecycleOwner, Observer {
                it?.let {  activitiesLiveData.postValue(it as ActivityData) }
                })
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}