package com.tcc.money.network.retrofit

import android.content.Context
import com.tcc.money.data.services.AuthenticateService
import com.tcc.money.network.api.CoinsApi
import com.tcc.money.network.api.MovementsApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovementsRetrofit {
    private const val BASE_URL = "http://localhost:8080"

    fun create(context: Context): MovementsApi {
        val token = AuthenticateService.getToken(context)

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val req = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(req)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovementsApi::class.java)
    }
}