// IsApiAvailableNowService.kt
package com.tcc.money.data.services

import android.content.Context
import android.util.Log
import com.tcc.money.data.services.AuthenticateService

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.logging.HttpLoggingInterceptor

@Singleton
class IsApiAvailableNowService @Inject constructor(
    private val networkIsConnectedService: NetworkIsConnectedService
) {
    companion object {
        private const val TAG = "IsApiAvailableNowService"
        private const val BASE_URL = "http://10.0.0.150:8080/api/user/online"
    }

    private val httpLogger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(httpLogger)
        .connectTimeout(2, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.SECONDS)
        .build()

    suspend fun execute(context: Context): Boolean = withContext(Dispatchers.IO) {
        if (!networkIsConnectedService.isConnected(context)) {
            Log.d(TAG, "Network is not connected")
            return@withContext false
        }
        val token = AuthenticateService.getToken(context).orEmpty()
        if (token.isBlank()) {
            Log.d(TAG, "No access token available")
            return@withContext false
        }
        val request = Request.Builder()
            .url(BASE_URL)
            .head()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return@withContext try {
            client.newCall(request).execute().use { response ->
                val ok = response.isSuccessful
                if (!ok) Log.d(TAG, "API unavailable or unauthorized (code=${response.code})")
                ok
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error checking API availability", e)
            false
        }
    }
}
