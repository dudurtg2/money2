package com.tcc.money.network.retrofit

import com.tcc.money.network.api.CoinsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoinsRetrofit {
    private const val BASE_URL = "http://localhost:8080"

    val instance: CoinsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinsApi::class.java)
    }
}