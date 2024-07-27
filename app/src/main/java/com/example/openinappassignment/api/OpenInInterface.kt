package com.example.openinappassignment.api

import com.example.openinappassignment.model.Dashboard
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface OpenInInterface {

    @GET("/api/v1/dashboardNew")

    suspend fun getDashboardData(
        @Header("Authorization") accessToken: String
    ):Response<Dashboard>

}