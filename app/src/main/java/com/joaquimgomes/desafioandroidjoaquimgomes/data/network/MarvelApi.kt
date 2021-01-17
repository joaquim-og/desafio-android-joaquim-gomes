package com.joaquimgomes.desafioandroidjoaquimgomes.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharacterComicApiResult
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Character
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Comics
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharactersApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import io.reactivex.Observable

interface MarvelApi {

    @GET("/v1/public/characters?orderBy=name")
    fun allCharactersInfo(@Query("ts") ts: String, @Query("apikey") apikey: String, @Query("hash") hash: String): Call<CharactersApiResult>

    @GET("/v1/public/characters/{id}/comics?limit=100&format=comic&formatType=comic")
    fun getCharacterComics(@Path("id") id: Int, @Query("ts") ts: String, @Query("apikey") apikey: String, @Query("hash") hash: String): Call<CharacterComicApiResult>

    @GET("/v1/public/characters/{id}/comics?limit=100&format=comic&formatType=comic")
    fun getMoreCharacterComics(@Path("id") id: Int, @Query("offset") offset: Int?, @Query("ts") ts: String, @Query("apikey") apikey: String, @Query("hash") hash: String): Call<CharacterComicApiResult>

}