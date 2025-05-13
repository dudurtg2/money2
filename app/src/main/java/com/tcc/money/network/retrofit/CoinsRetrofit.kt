package com.tcc.money.network.retrofit

import android.content.Context
import com.tcc.money.data.services.AuthenticateService
import com.tcc.money.network.api.CoinsApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoinsRetrofit {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    fun create(context: Context): CoinsApi {
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
            .create(CoinsApi::class.java)
    }
}