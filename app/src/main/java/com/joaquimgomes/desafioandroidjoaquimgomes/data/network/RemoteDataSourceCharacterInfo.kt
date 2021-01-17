package com.joaquimgomes.desafioandroidjoaquimgomes.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Character
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharacterComicApiResult
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Comics
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharactersApiResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.joaquimgomes.desafioandroidjoaquimgomes.data.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit

class RemoteDataSourceCharacterInfo {

    private val retrofitClient = RetrofitClient().getRetrofitInstance("https://gateway.marvel.com")
    private val endpoint = retrofitClient.create(MarvelApi::class.java)

    fun getCharacterInfo(ts: String, apikey: String, hash: String): Call<CharactersApiResult> =
        endpoint.allCharactersInfo(ts, apikey, hash)

    fun getCharacterComics(
        characterId: Int,
        ts: String,
        apikey: String,
        hash: String
    ): Call<CharacterComicApiResult> = endpoint.getCharacterComics(characterId, ts, apikey, hash)

    fun getMoreCharacterComics(characterId: Int, offsetParamInThisRequest: Int?, ts: String, apikey: String, hash: String): Call<CharacterComicApiResult> = endpoint.getMoreCharacterComics(characterId, offsetParamInThisRequest, ts, apikey, hash)

}


