package com.example.openinappassignment.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object OpenInAPI {

    private const val BASE_URL = "https://api.inopenapp.com"

    fun getInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}