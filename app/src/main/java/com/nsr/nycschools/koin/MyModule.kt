package com.nsr.nycschools.koin

import com.nsr.nycschools.network.ApiHelper
import com.nsr.nycschools.network.NYCAPIClient
import com.nsr.nycschools.viewmodel.NycSchoolsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    // Repository
    single<ApiHelper> { ApiHelper(NYCAPIClient.apiService) }

    // ViewModel
    viewModel<NycSchoolsViewModel> { NycSchoolsViewModel(get()) }
}