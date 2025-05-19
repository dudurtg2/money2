package com.tcc.money.data.services

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object AuthenticateService {

    private const val PREFS_FILENAME = "secure_prefs"
    private const val TOKEN_KEY = "EUAMORMEUAMORETERNOAMOLINDAMARAVILHOSA_ACCESS_TOKEN_KEY"
    private const val REFRESH_TOKEN_KEY = "EUAMORMEUAMORETERNOAMOLINDAMARAVILHOSA_REFRESH_TOKEN_KEY"

    private fun getEncryptedPrefs(context: Context) =
        EncryptedSharedPreferences.create(
            context,
            PREFS_FILENAME,
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )


    fun saveToken(context: Context, token: String) {
        val prefs = getEncryptedPrefs(context)
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(context: Context): String? {
        val prefs = getEncryptedPrefs(context)
        return prefs.getString(TOKEN_KEY, null)
    }

    fun saveRefreshToken(context: Context, refreshToken: String) {
        val prefs = getEncryptedPrefs(context)
        prefs.edit().putString(REFRESH_TOKEN_KEY, refreshToken).apply()
    }

    fun getRefreshToken(context: Context): String? {
        val prefs = getEncryptedPrefs(context)
        return prefs.getString(REFRESH_TOKEN_KEY, null)
    }

    fun clearToken(context: Context) {
        val prefs = getEncryptedPrefs(context)
        prefs.edit()
            .remove(TOKEN_KEY)
            .remove(REFRESH_TOKEN_KEY)
            .apply()
    }
}


