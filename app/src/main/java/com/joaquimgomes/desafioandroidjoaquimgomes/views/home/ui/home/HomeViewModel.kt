package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.GetCurrency
import com.joaquimgomes.desafioandroidjoaquimgomes.data.repository.RepositoryCharacterInfo
import com.joaquimgomes.desafioandroidjoaquimgomes.data.repository.RepositoryCharacterInfoImpl

class HomeViewModel(private val repository: RepositoryCharacterInfo = RepositoryCharacterInfoImpl()): ViewModel() {

    private val localeNumberFormatter = GetCurrency().localeCurrency()

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

}