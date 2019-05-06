package com.agzz.kineducatalog.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.agzz.kineducatalog.entities.ActivityData
import com.agzz.kineducatalog.network.Resource
import com.agzz.kineducatalog.repositories.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ActivityViewModel : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : Repository = Repository


    val activitiesLiveData = MutableLiveData<Resource<ActivityData>>()

    fun fetchActivities(skillId:String, babyId: String, lifecycleOwner: LifecycleOwner){
        scope.launch(Dispatchers.Main) {
            repository.getActivities(skillId,babyId).observe(lifecycleOwner, Observer {  activitiesLiveData.postValue(it) })
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}