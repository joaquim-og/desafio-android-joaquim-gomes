package com.joaquimgomes.desafioandroidjoaquimgomes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Character
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharacterComicApiResult
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Comics
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.CharactersApiResult

interface RepositoryCharacterInfo {

    val listCharacterData: MutableLiveData<List<Character>?>

    val listCharacterComic: MutableLiveData<MutableList<Comics>?>

    fun getCharactersInfo(ts: String, apikey: String, hash: String)

    fun getCharacterComics(characterId: Int, ts: String, apikey: String, hash: String)

}