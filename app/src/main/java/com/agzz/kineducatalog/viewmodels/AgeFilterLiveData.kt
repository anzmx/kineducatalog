package com.agzz.kineducatalog.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class FilterValueViewModel (var app: Application) : AndroidViewModel(app){
    val selectedAge = MutableLiveData<Int>()
    init {
        selectedAge.value = 0
    }
}