package com.joaquimgomes.desafioandroidjoaquimgomes.data.network

import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharacterComicApiResult
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Comics
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharactersApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters?orderBy=name")
    fun allCharactersInfo(@Query("ts") ts: String, @Query("apikey") apikey: String, @Query("hash") hash: String): Call<CharactersApiResult>

    @GET("/v1/public/characters/{id}/comics?format=comic&formatType=comic")
    fun getCharacterComics(@Path("id") id: Int, @Query("ts") ts: String, @Query("apikey") apikey: String, @Query("hash") hash: String): Call<CharacterComicApiResult>

}