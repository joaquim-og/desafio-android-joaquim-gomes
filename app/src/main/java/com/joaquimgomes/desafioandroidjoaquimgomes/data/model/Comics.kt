package com.joaquimgomes.desafioandroidjoaquimgomes.data.model

import com.google.gson.annotations.SerializedName

data class Comics (

    @SerializedName("id")
    var id: Number? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("prices")
    var prices: MutableList<ComicsPricesInfo>,
    @SerializedName("thumbnail")
    var characterComicsThumbnail: ComicsThumbnailInfo

)

data class ListComics(

    @SerializedName("results")
    var listComics: MutableList<Comics>

)

data class CharacterComicApiResult(

    @SerializedName("data")
    var data: ListComics
)

data class ComicsThumbnailInfo(

    @SerializedName("path")
    var path: String? = null,
    @SerializedName("extension")
    var extension: String? = null

)

data class ComicsPricesInfo(

    @SerializedName("type")
    var type: String? = null,
    @SerializedName("price")
    var price: Number? = null

)