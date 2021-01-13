package com.joaquimgomes.desafioandroidjoaquimgomes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharacterComicApiResult
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharactersApiResult
import com.joaquimgomes.desafioandroidjoaquimgomes.data.network.RemoteDataSourceCharacterInfo
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryCharacterInfoImpl : RepositoryCharacterInfo {

    private val remoteDataSource = RemoteDataSourceCharacterInfo()
    private val compositeDisposable = CompositeDisposable()

    override fun getCharactersInfo(ts: String, apikey: String, hash: String): LiveData<CharactersApiResult> {

        val data = MutableLiveData<CharactersApiResult>()

        val characterInfos = remoteDataSource.getCharacterInfo(ts, apikey, hash)

        characterInfos.enqueue(object : Callback<CharactersApiResult> {

            override fun onResponse(
                call: Call<CharactersApiResult>,
                response: Response<CharactersApiResult>
            ) {
                if (response.isSuccessful) {

                    //TODO set data value
//                    data.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<CharactersApiResult>, e: Throwable) {
                e.printStackTrace()
                data.postValue(null)
            }


        })

        return data

    }

    override fun getCharacterComics(characterId: Int, ts: String, apikey: String, hash: String): LiveData<CharacterComicApiResult> {

        val data = MutableLiveData<CharacterComicApiResult>()

        val characterComicsInfos = remoteDataSource.getCharacterComics(characterId, ts, apikey, hash)

        characterComicsInfos.enqueue(object:  Callback<CharacterComicApiResult> {

            override fun onResponse(
                call: Call<CharacterComicApiResult>,
                response: Response<CharacterComicApiResult>
            ) {
                if (response.isSuccessful) {

                    //TODO set data value
//                    data.postValue(response.body())
                }
                println("aqui as infos da REQUEST dos COMICS DOS Characters -> ${response.body().toString()}")

                val testeJSON = Gson().toJson(response.body())
                println("aqui as infos dos COMICS DO REQUEST dos Characters, TENTANDO PARSEAR JSON OBJECT -> $testeJSON")

                val testeData = GsonBuilder().create().fromJson(testeJSON, CharacterComicApiResult::class.java) //JSONObject(testeJSON)
                println("aqui as infos dos COMICS DO REQUEST dos Characters, TENTANDO PARSEAR ACESSANDO O JSON QUE QUERO -> $testeData")

                for (i in testeData.data.listComics){
                    println("aqui as infos dos COMICS DO dos Characters, printando os dados dO JSON QUE QUERO -> ${i.title} | ${i.id} | ${i.prices} | ${i.characterComicsThumbnail.path} + ${i.characterComicsThumbnail.extension}")
                }

            }

            override fun onFailure(call: Call<CharacterComicApiResult>, e: Throwable) {
                e.printStackTrace()
                data.postValue(null)
            }


        })

        return data

    }

}