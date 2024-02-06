package com.nsr.nycschools.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsr.nycschools.R
import com.nsr.nycschools.model.NycSchoolsResponse
import com.nsr.nycschools.network.ApiHelper
import com.nsr.nycschools.network.ResponseResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class NycSchoolsViewModel(private val apiHelper: ApiHelper): ViewModel() {

    private val _nycSchoolsList = MutableStateFlow<ResponseResource<List<NycSchoolsUiModel>>>(
        ResponseResource.loading())
    val nycSchoolsList: MutableStateFlow<ResponseResource<List<NycSchoolsUiModel>>> = _nycSchoolsList

    init {
        fetchNycSchoolsList()
    }

    private fun fetchNycSchoolsList() {
        _nycSchoolsList.value = ResponseResource.loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = apiHelper.getNycSchoolsInfo()
                    processResponse(response)
                } catch (e: Exception) {
                    Log.e("TAG ", e.message.orEmpty())
                    _nycSchoolsList.value = ResponseResource.error(R.string.error_message)
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
            _nycSchoolsList.value = ResponseResource.success(list)
        }
    }

}

data class NycSchoolsUiModel(
    val dbn: String? = null,
    val schoolName: String? = null,
    val overView: String? = null,
)