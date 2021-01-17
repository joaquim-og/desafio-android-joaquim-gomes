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

    override val listCharacterComic = MutableLiveData<MutableList<Comics>?>()

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

                    val firstListComics = responseData.data.listComics
                    val totalCharacterComics = responseData.data.total
                    val countAllComicsInThisResponse = responseData.data.count
                    val offsetParamInThisResponse = responseData.data.offset

                    if (totalCharacterComics!! < countAllComicsInThisResponse!!) {

                        requestServerAdditionalInfo(
                            firstListComics,
                            totalCharacterComics,
                            offsetParamInThisResponse,
                            characterId,
                            ts,
                            apikey,
                            hash
                        )

                    } else if (totalCharacterComics == 0) {

                        listCharacterComic.postValue(null)

                    } else {

                        listCharacterComic.postValue(firstListComics)

                    }
                }
            }

            override fun onFailure(call: Call<CharacterComicApiResult>, e: Throwable) {
                e.printStackTrace()
            }

        })
    }

    private fun requestServerAdditionalInfo(
        ListComics: MutableList<Comics>,
        totalCharacterComics: Int,
        offsetParamInPreviousResponse: Int?,
        characterId: Int,
        ts: String,
        apikey: String,
        hash: String
    ) {

        val listComics = ListComics

        val offsetParamInThisRequest = offsetParamInPreviousResponse?.plus(listComics.size)

        val getCharacterMoreComicInfos = remoteDataSource.getMoreCharacterComics(
            characterId,
            offsetParamInThisRequest,
            ts,
            apikey,
            hash
        )

        getCharacterMoreComicInfos.enqueue(object : Callback<CharacterComicApiResult> {

            override fun onResponse(
                call: Call<CharacterComicApiResult>,
                response: Response<CharacterComicApiResult>
            ) {

                if (response.isSuccessful) {

                    val responseJSON = Gson().toJson(response.body())
                    val responseData = GsonBuilder().create()
                        .fromJson(responseJSON, CharacterComicApiResult::class.java)

                    val newResponseListComics = responseData.data.listComics
                    val newCountAllComicsInThisResponse = responseData.data.count

                    for (comic in newResponseListComics) {
                        listComics.add(comic)
                    }

                    if (totalCharacterComics!! < offsetParamInThisRequest?.plus(
                            newCountAllComicsInThisResponse!!
                        )!!
                    ) {

                        getCharacterMoreComics(
                            listComics,
                            newCountAllComicsInThisResponse!!,
                            offsetParamInThisRequest!!,
                            characterId,
                            ts,
                            apikey,
                            hash
                        )

                    } else {

                        listCharacterComic.postValue(listComics)

                    }

                }

            }

            override fun onFailure(call: Call<CharacterComicApiResult>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun getCharacterMoreComics(
        listComics: MutableList<Comics>,
        countAllComicsInPreviousResponse: Int,
        offsetParamInPreviousRequest: Int,
        characterId: Int,
        ts: String,
        apikey: String,
        hash: String
    ) {

        requestServerAdditionalInfo(
            listComics,
            countAllComicsInPreviousResponse,
            offsetParamInPreviousRequest,
            characterId,
            ts,
            apikey,
            hash
        )

    }
}

