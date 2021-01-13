package com.joaquimgomes.desafioandroidjoaquimgomes.data.network

import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharacterComicApiResult
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Comics
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharactersApiResult
import retrofit2.Call


class RemoteDataSourceCharacterInfo {

    private val retrofitClient = RetrofitClient().getRetrofitInstance("https://gateway.marvel.com")
    private val endpoint = retrofitClient.create(MarvelApi::class.java)

    fun getCharacterInfo(ts: String, apikey: String, hash: String): Call<CharactersApiResult> = endpoint.allCharactersInfo(ts, apikey, hash)

    fun getCharacterComics(characterId: Int, ts: String, apikey: String, hash: String): Call<CharacterComicApiResult> = endpoint.getCharacterComics(characterId, ts, apikey, hash)

}