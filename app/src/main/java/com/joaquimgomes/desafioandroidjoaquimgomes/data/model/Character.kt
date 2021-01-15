package com.joaquimgomes.desafioandroidjoaquimgomes.data.model

import com.google.gson.annotations.SerializedName

data class Character(

    @SerializedName("id")
    var id: Number? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("thumbnail")
    var characterThumbnail: CharacterThumbnailInfo

)

data class ListCharacter(

    @SerializedName("results")
    var listCharacter: List<Character>

)

data class CharactersApiResult(

    @SerializedName("data")
    var data: ListCharacter
)

data class CharacterThumbnailInfo(

    @SerializedName("path")
    var path: String? = null,
    @SerializedName("extension")
    var extension: String? = null

)