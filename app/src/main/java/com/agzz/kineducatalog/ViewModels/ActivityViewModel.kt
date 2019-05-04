package com.agzz.kineducatalog.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agzz.kineducatalog.Entities.Activity
import com.agzz.kineducatalog.Entities.ActivityData
import com.agzz.kineducatalog.Network.Resource
import com.agzz.kineducatalog.Repositories.KineduAPI
import com.agzz.kineducatalog.Repositories.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ActivityViewModel : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : Repository = Repository


    val activitiesLiveData = MutableLiveData<MutableLiveData<Resource<ActivityData>>>()

    fun fetchActivities(){
        scope.launch {
            val activities = repository.getActivities("5","2064732")
            activitiesLiveData.postValue(activities)
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}