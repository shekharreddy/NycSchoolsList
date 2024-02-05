package com.nsr.nycschools.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsr.nycschools.model.NycSchoolsResponse
import com.nsr.nycschools.network.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class NycSchoolsViewModel(private val apiHelper: ApiHelper): ViewModel() {

    private var _nycSchoolsList = MutableStateFlow(listOf(NycSchoolsUiModel()))
    val nycSchoolsList: MutableStateFlow<List<NycSchoolsUiModel>> get() = _nycSchoolsList

    init {
        fetchNycSchoolsList()
    }

    private fun fetchNycSchoolsList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = apiHelper.getNycSchoolsInfo()
                    processResponse(response)
                } catch (e: Exception) {
                    Log.e("TAG ", e.message.orEmpty())
                    _nycSchoolsList.value = emptyList()
                }
            }
        }
    }

    private fun processResponse(res: NycSchoolsResponse){
        val list = mutableListOf<NycSchoolsUiModel>()
        res.forEach {
            list.add(NycSchoolsUiModel(
                dbn = it.dbn,
                schoolName = it.school_name,
                overView = it.overview_paragraph
            )
            )
        }.also {
            _nycSchoolsList.value = list
        }
    }

}

data class NycSchoolsUiModel(
    val dbn: String? = null,
    val schoolName: String? = null,
    val overView: String? = null,
)