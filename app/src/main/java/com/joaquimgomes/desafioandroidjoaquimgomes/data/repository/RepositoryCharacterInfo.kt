package com.joaquimgomes.desafioandroidjoaquimgomes.data.repository

import androidx.lifecycle.MutableLiveData
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Character
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.Comics

interface RepositoryCharacterInfo {

    val listCharacterData: MutableLiveData<List<Character>?>

    val listCharacterComic: MutableLiveData<MutableList<Comics>?>

    val hasAllServerCharacterDataLoaded: MutableLiveData<Boolean>

    fun getCharactersInfo(ts: String, apikey: String, hash: String, queryServerOffSet: Int)

    fun getCharacterComics(characterId: Int, ts: String, apikey: String, hash: String)

}