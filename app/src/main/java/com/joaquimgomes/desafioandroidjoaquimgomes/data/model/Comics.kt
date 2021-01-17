package com.joaquimgomes.desafioandroidjoaquimgomes.data.model

import com.google.gson.annotations.SerializedName

data class Comics (

    @SerializedName("id")
    var id: Number? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("prices")
    var prices: List<ComicsPricesInfo>,
    @SerializedName("thumbnail")
    var characterComicsThumbnail: ComicsThumbnailInfo

)

data class ListComics(

    @SerializedName("results")
    var listComics: List<Comics>

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

data class ComicsWithHighestPrice (

    var idComic: Int? = null,
    var imgComic: String? = null,
    var titleComic: String? = null,
    var descriptionComic: String? = null,
    var highestPrice: Number? = null

)