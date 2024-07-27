package com.example.openinappassignment.fragments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openinappassignment.model.Dashboard
import com.example.openinappassignment.repository.OpenInAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LinkViewModel(
    private val repository: OpenInAppRepository,
    private val accessToken:String
) : ViewModel() {

   init {
       viewModelScope.launch(Dispatchers.IO){
           repository.getDashboard(accessToken)
       }
   }

    val dashboard : LiveData<Dashboard>
        get() = repository.dashboard

}