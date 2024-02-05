package com.nsr.nycschools.network

import com.nsr.nycschools.model.NycSchoolsResponse
import retrofit2.http.GET

interface NYCApiService {
    @GET("/resource/s3k6-pzi2.json")
    suspend fun getNycSchoolsList(): NycSchoolsResponse
}