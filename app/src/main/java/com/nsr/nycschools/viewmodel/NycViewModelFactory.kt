package com.nsr.nycschools.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nsr.nycschools.network.ApiHelper
import com.nsr.nycschools.network.NYCApiService

class NycViewModelFactory(
    private val apiHelper: ApiHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NycSchoolsViewModel::class.java)){
            return NycSchoolsViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}