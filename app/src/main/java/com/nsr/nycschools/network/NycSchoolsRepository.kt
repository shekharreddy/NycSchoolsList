package com.nsr.nycschools.network

import com.nsr.nycschools.model.NycSchoolsResponseItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface NycSchoolsRepository {
    suspend fun getNycSchoolsInfo(): Result<List<NycSchoolsResponseItem>>
}

class NycSchoolsRepositoryImpl(private val httpClient: HttpClient) : NycSchoolsRepository {
    override suspend fun getNycSchoolsInfo(): Result<List<NycSchoolsResponseItem>> {
        return runCatching {
            httpClient.get(NYCAPIClient.NYC_SCHOOLS_ENDPOINT).body()
        }
    }
}