package com.joaquimgomes.desafioandroidjoaquimgomes.views.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joaquimgomes.desafioandroidjoaquimgomes.data.commom.GetCurrency
import com.joaquimgomes.desafioandroidjoaquimgomes.data.model.*
import com.joaquimgomes.desafioandroidjoaquimgomes.data.repository.RepositoryCharacterInfo
import com.joaquimgomes.desafioandroidjoaquimgomes.data.repository.RepositoryCharacterInfoImpl
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class HomeViewModel(private val repository: RepositoryCharacterInfo = RepositoryCharacterInfoImpl()) :
    ViewModel() {

    private val localeNumberFormatter = GetCurrency().localeCurrency()
    private val ts = Timestamp(System.currentTimeMillis()).time.toString()
    private val apikey = "4b673fea0d752a6b17e9690293aa123d"
    private val hash = makeMarvelHash()

    private val _charactersServerData = repository.listCharacterData
    val charactersServerData: LiveData<List<Character>?>
        get() = _charactersServerData

    private val _characterComicsServerData = repository.listCharacterComic
    val characterComicsServerData: LiveData<MutableList<Comics>?>
        get() = _characterComicsServerData

    private val _hasAllServerCharacterDataLoaded = repository.hasAllServerCharacterDataLoaded
    val hasAllServerCharacterDataLoaded: MutableLiveData<Boolean>
        get() = _hasAllServerCharacterDataLoaded


    fun getAllCharactersInfo(queryServerOffSet: Int) = repository.getCharactersInfo(ts, apikey, hash, queryServerOffSet)



    fun getAllCharacterComics(characterId: Number?) = characterId?.toInt()?.let { repository.getCharacterComics(it, ts, apikey, hash) }


    fun getCharacterMostExpensiveComic(comics: List<Comics>): ComicsWithHighestPrice? {

        val comicsIdAndHighestPrice = mutableListOf<ComicsWithHighestPrice>()

        comics.forEach { comic ->

            val idComic = comic.id?.toInt()!!
            val imgComic =
                comic.characterComicsThumbnail.path + "." + comic.characterComicsThumbnail.extension
            val titleComic = comic.title
            val descriptionComic = comic.description

            val pricesListComplete = comic.prices
            val highestPrice = pricesListComplete.maxByOrNull { listPrices ->
                listPrices.price?.toDouble()!!
            }

            val comicList =
                ComicsWithHighestPrice(
                    idComic,
                    imgComic,
                    titleComic,
                    descriptionComic,
                    highestPrice?.price)


            comicsIdAndHighestPrice.add(comicList)


        }

        return comicsIdAndHighestPrice.maxByOrNull { comicMostExpensive ->
            comicMostExpensive.highestPrice?.toDouble()!!
        }

    }


    private fun makeMarvelHash(): String {

        val messageDigest = MessageDigest.getInstance("MD5")
        val input = ts.toString() + "46ea55eac09fed97f3886a915add17175c160f50" + apikey

        return BigInteger(1, messageDigest.digest(input.toByteArray())).toString(16)
            .padStart(32, '0')

    }

    fun clearAllCharactersInfo() {

        _charactersServerData.value = emptyList()
        _characterComicsServerData.value = mutableListOf<Comics>()
        _characterComicsServerData.notifyObserver()
        _charactersServerData.notifyObserver()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

}
