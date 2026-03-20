package com.nsr.nycschools.network

import com.nsr.nycschools.model.NycSchoolsResponseItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiHelper(private val httpClient: HttpClient) {

    suspend fun getNycSchoolsInfo(): List<NycSchoolsResponseItem> {
        return httpClient.get(NYCAPIClient.NYC_SCHOOLS_ENDPOINT).body()
    }
}