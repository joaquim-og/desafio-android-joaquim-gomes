package com.joaquimgomes.desafioandroidjoaquimgomes.data.network

import android.content.res.Resources
import com.joaquimgomes.desafioandroidjoaquimgomes.R
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    fun getRetrofitInstance(path: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(path)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

