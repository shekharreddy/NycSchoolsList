package com.nsr.nycschools.network

class ApiHelper(private val apiService: NYCApiService) {

    suspend fun getNycSchoolsInfo() = apiService.getNycSchoolsList()
}