package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joaquimgomes.desafioandroidjoaquimgomes.R
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.GetCurrency
import com.joaquimgomes.desafioandroidjoaquimgomes.data.repository.RepositoryCharacterInfo
import com.joaquimgomes.desafioandroidjoaquimgomes.data.repository.RepositoryCharacterInfoImpl
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Date
import java.sql.Timestamp

class HomeViewModel(private val repository: RepositoryCharacterInfo = RepositoryCharacterInfoImpl()): ViewModel() {

    private val localeNumberFormatter = GetCurrency().localeCurrency()
    private val ts = Timestamp(System.currentTimeMillis()).time.toString()
    private val apikey = "4b673fea0d752a6b17e9690293aa123d"
    private val hash = makeMarvelHash()

    fun getAllCharactersInfo() {

        val infos = repository.getCharactersInfo(ts, apikey, hash)

        println("aqui as infos dos Characters que pegamos -> $ts")
        println("aqui as infos dos Characters que pegamos -> $apikey")
        println("aqui as infos dos Characters que pegamos -> $hash")
        println("aqui as infos dos Characters que pegamos -> ${infos.value}")

    }

    fun getCharacterComics(idCharacter: Int){

        val comics = repository.getCharacterComics(idCharacter, ts, apikey, hash)

        println("aqui as info das COMICS que pegamos do THOR -> $ts")
        println("aqui as info das COMICS que pegamos do THOR -> $apikey")
        println("aqui as info das COMICS que pegamos do THOR -> $hash")
        println("aqui as info das COMICS que pegamos do THOR -> ${comics.value}")

    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun makeMarvelHash(): String {

        val messageDigest = MessageDigest.getInstance("MD5")
        val input = ts.toString() + "46ea55eac09fed97f3886a915add17175c160f50" + apikey

        return BigInteger(1,messageDigest.digest(input.toByteArray())).toString(16).padStart(32, '0')

    }

}