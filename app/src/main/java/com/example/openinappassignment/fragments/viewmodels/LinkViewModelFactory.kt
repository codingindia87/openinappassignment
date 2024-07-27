package com.example.openinappassignment.fragments.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.openinappassignment.repository.OpenInAppRepository

class LinkViewModelFactory(private val repository: OpenInAppRepository,private val accessToken: String):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LinkViewModel(repository,accessToken) as T
    }
}