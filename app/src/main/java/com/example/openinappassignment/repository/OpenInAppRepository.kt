package com.example.openinappassignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openinappassignment.api.OpenInInterface
import com.example.openinappassignment.model.Dashboard

class OpenInAppRepository(private val openInInterface: OpenInInterface
) {

    private val dashboardLiveData = MutableLiveData<Dashboard>()

    val dashboard: LiveData<Dashboard>
        get() = dashboardLiveData

    suspend fun getDashboard(accessToken: String){
        val result = openInInterface.getDashboardData(accessToken)
        if (result.body() != null){
            dashboardLiveData.postValue(result.body())
        }
    }
}