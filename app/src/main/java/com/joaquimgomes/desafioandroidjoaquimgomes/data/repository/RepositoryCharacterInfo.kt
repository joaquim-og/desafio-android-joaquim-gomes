package com.joaquimgomes.desafioandroidjoaquimgomes.data.repository

import androidx.lifecycle.LiveData
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharacterComicApiResult
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Comics
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharactersApiResult

interface RepositoryCharacterInfo {

    fun getCharactersInfo(ts: String, apikey: String, hash: String): LiveData<CharactersApiResult>
    fun getCharacterComics(characterId: Int, ts: String, apikey: String, hash: String): LiveData<CharacterComicApiResult>

}