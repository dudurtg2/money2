package com.tcc.money.network.retrofit

import com.tcc.money.network.api.CoinsApi
import com.tcc.money.network.api.MovementsApi
import com.tcc.money.network.api.UsersApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsersRetrofit {
    private const val BASE_URL = "http://localhost:8080"

    val instance: UsersApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsersApi::class.java)
    }
}