package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.GetCurrency
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Character
import com.joaquimgomes.desafioandroidjoaquimgomes.data.repository.RepositoryCharacterInfo
import com.joaquimgomes.desafioandroidjoaquimgomes.data.repository.RepositoryCharacterInfoImpl
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class HomeViewModel(private val repository: RepositoryCharacterInfo = RepositoryCharacterInfoImpl()): ViewModel() {

    private val localeNumberFormatter = GetCurrency().localeCurrency()
    private val ts = Timestamp(System.currentTimeMillis()).time.toString()
    private val apikey = "4b673fea0d752a6b17e9690293aa123d"
    private val hash = makeMarvelHash()

    private val _charactersServerData = repository.listCharacterData
    val charactersServerData: LiveData<MutableList<Character>>
        get() = _charactersServerData


    fun getAllCharactersInfo() {
        repository.getCharactersInfo(ts, apikey, hash)
    }


    private fun makeMarvelHash(): String {

        val messageDigest = MessageDigest.getInstance("MD5")
        val input = ts.toString() + "46ea55eac09fed97f3886a915add17175c160f50" + apikey

        return BigInteger(1,messageDigest.digest(input.toByteArray())).toString(16).padStart(32, '0')

    }

    fun clearListCharacters() {
        _charactersServerData.postValue(null)
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

}