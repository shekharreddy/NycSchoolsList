package com.nsr.nycschools.koin

import com.nsr.nycschools.network.NYCAPIClient
import com.nsr.nycschools.network.NycSchoolsRepository
import com.nsr.nycschools.network.NycSchoolsRepositoryImpl
import com.nsr.nycschools.viewmodel.NycSchoolsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    // Ktor HttpClient
    single { NYCAPIClient.httpClient }

    // Repository
    single<NycSchoolsRepository> { NycSchoolsRepositoryImpl(get()) }

    // ViewModel
    viewModel { NycSchoolsViewModel(get()) }
}