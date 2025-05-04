package com.tcc.money.network.retrofit

import com.tcc.money.network.api.CoinsApi
import com.tcc.money.network.api.MovementsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovementsRetrofit {
    private const val BASE_URL = "http://localhost:8080"

    val instance: MovementsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovementsApi::class.java)
    }
}