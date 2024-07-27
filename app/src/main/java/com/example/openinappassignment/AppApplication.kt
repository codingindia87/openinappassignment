package com.example.openinappassignment

import android.app.Application
import com.example.openinappassignment.api.OpenInAPI
import com.example.openinappassignment.api.OpenInInterface
import com.example.openinappassignment.repository.OpenInAppRepository
import com.example.openinappassignment.utils.AccessToken

class AppApplication: Application() {

    lateinit var openInAppRepository: OpenInAppRepository

    override fun onCreate() {
        super.onCreate()
        AccessToken(this).setAccessToken()
        initialize()
    }

    private fun initialize() {
        val openInInterface = OpenInAPI.getInstance().create(OpenInInterface::class.java)
        openInAppRepository = OpenInAppRepository(openInInterface)
    }
}