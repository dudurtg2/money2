package com.tcc.money.data.services

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class IsApiAvailableNowService(
    private val baseUrl: String = "http://10.0.0.150:8080/api/user/online"
) {

    private val httpLogger = HttpLoggingInterceptor { message ->

    }.apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(httpLogger)
        .connectTimeout(2, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.SECONDS)
        .build()

    fun execute(context: Context): Boolean {
        if(!NetworkIsConnectedService().isConnected(context)){
            Log.d("LoginUseCase", "Network is n connected")
            return false
        }
        return try {
            val request = Request.Builder()
                .url(baseUrl)
                .head()
                .build()

            client.newCall(request).execute().use { response ->
                val ok = response.isSuccessful
                ok
            }
        } catch (e: IOException) {
            false
        }
    }
}
