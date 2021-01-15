package com.joaquimgomes.desafioandroidjoaquimgomes.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Character
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharacterComicApiResult
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharactersApiResult
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Comics
import com.joaquimgomes.desafioandroidjoaquimgomes.data.network.RemoteDataSourceCharacterInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryCharacterInfoImpl : RepositoryCharacterInfo {

    private val remoteDataSource = RemoteDataSourceCharacterInfo()

    override val listCharacterData = MutableLiveData<List<Character>?>()

    override val listCharacterComic = MutableLiveData<List<Comics>?>()

    override fun getCharactersInfo(
        ts: String,
        apikey: String,
        hash: String
    ) {

        val characterInfos = remoteDataSource.getCharacterInfo(ts, apikey, hash)

        characterInfos.enqueue(object : Callback<CharactersApiResult> {

            override fun onResponse(
                call: Call<CharactersApiResult>,
                response: Response<CharactersApiResult>
            ) {

                if (response.isSuccessful) {

                    val responseJSON = Gson().toJson(response.body())
                    val responseData = GsonBuilder().create()
                        .fromJson(responseJSON, CharactersApiResult::class.java)

                    listCharacterData.postValue(responseData.data.listCharacter)

                } else {

                    listCharacterData.postValue(null)

                }

            }

            override fun onFailure(call: Call<CharactersApiResult>, e: Throwable) {
                e.printStackTrace()
            }

        })

    }

    override fun getCharacterComics(
        characterId: Int,
        ts: String,
        apikey: String,
        hash: String
    ) {

        val characterComicInfos = remoteDataSource.getCharacterComics(characterId, ts, apikey, hash)

        characterComicInfos.enqueue(object : Callback<CharacterComicApiResult> {

                override fun onResponse(
                    call: Call<CharacterComicApiResult>,
                    response: Response<CharacterComicApiResult>
                ) {

                    if (response.isSuccessful) {

                        val responseJSON = Gson().toJson(response.body())
                        val responseData = GsonBuilder().create()
                            .fromJson(responseJSON, CharacterComicApiResult::class.java)

                        listCharacterComic.postValue(responseData.data.listComics)

                    } else {

                        listCharacterComic.postValue(null)

                    }

                }

                override fun onFailure(call: Call<CharacterComicApiResult>, e: Throwable) {
                    e.printStackTrace()
                }

            })

    }

}