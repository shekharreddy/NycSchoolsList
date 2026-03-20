package com.nsr.nycschools.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsr.nycschools.R
import com.nsr.nycschools.model.NycSchoolsResponseItem
import com.nsr.nycschools.network.NycSchoolsRepository
import com.nsr.nycschools.network.ResponseResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NycSchoolsViewModel(private val repository: NycSchoolsRepository) : ViewModel() {

    private val _nycSchoolsList = MutableStateFlow<ResponseResource<List<NycSchoolsUiModel>>>(
        ResponseResource.loading()
    )
    val nycSchoolsList: StateFlow<ResponseResource<List<NycSchoolsUiModel>>> = _nycSchoolsList.asStateFlow()

    init {
        fetchNycSchoolsList()
    }

    fun fetchNycSchoolsList() {
        _nycSchoolsList.value = ResponseResource.loading()
        viewModelScope.launch {
            repository.getNycSchoolsInfo()
                .onSuccess { response ->
                    processResponse(response)
                }
                .onFailure { e ->
                    Log.e("NycSchoolsViewModel", "Error fetching schools", e)
                    _nycSchoolsList.value = ResponseResource.error(R.string.error_message)
                }
        }
    }

    private fun processResponse(res: List<NycSchoolsResponseItem>) {
        val list = res.map {
            NycSchoolsUiModel(
                dbn = it.dbn,
                schoolName = it.school_name,
                overView = it.overview_paragraph
            )
        }
        _nycSchoolsList.value = ResponseResource.success(list)
    }
}

data class NycSchoolsUiModel(
    val dbn: String? = null,
    val schoolName: String? = null,
    val overView: String? = null,
)