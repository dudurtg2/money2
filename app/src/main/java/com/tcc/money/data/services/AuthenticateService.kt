package com.tcc.money.data.services

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.crypto.tink.Aead
import com.google.crypto.tink.integration.android.AndroidKeystoreAesGcm
import java.io.IOException
import javax.crypto.AEADBadTagException

object AuthenticateService {

    private const val PREFS_FILENAME = "EUAMORMEUAMORETERNOAMOLINDAMARAVILHOSAsecure_prefs"
    private const val MASTER_KEY_ALIAS = "EUAMORMEUAMORETERNOAMOLINDAMARAVILHOSAeu_amor_app_master_key"
    private const val TOKEN_KEY = "EUAMORMEUAMORETERNOAMOLINDAMARAVILHOSA_ACCESS_TOKEN_KEY"
    private const val REFRESH_TOKEN_KEY = "EUAMORMEUAMORETERNOAMOLINDAMARAVILHOSA_REFRESH_TOKEN_KEY"

    private fun getEncryptedPrefs(context: Context) =
        try {
            val masterKey = MasterKey.Builder(context, MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            EncryptedSharedPreferences.create(
                context,
                PREFS_FILENAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: AEADBadTagException) {
            context.deleteFile(PREFS_FILENAME)

            val masterKey = MasterKey.Builder(context, MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            EncryptedSharedPreferences.create(
                context,
                PREFS_FILENAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: IOException) {
            throw RuntimeException("Não foi possível inicializar prefs seguros", e)
        }

    fun saveToken(context: Context, token: String) {
        getEncryptedPrefs(context)
            .edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    fun getToken(context: Context): String? {
        return getEncryptedPrefs(context)
            .getString(TOKEN_KEY, null)
    }

    fun saveRefreshToken(context: Context, refreshToken: String) {
        getEncryptedPrefs(context)
            .edit()
            .putString(REFRESH_TOKEN_KEY, refreshToken)
            .apply()
    }

    fun getRefreshToken(context: Context): String? {
        return getEncryptedPrefs(context)
            .getString(REFRESH_TOKEN_KEY, null)
    }

    fun clearToken(context: Context) {
        getEncryptedPrefs(context)
            .edit()
            .remove(TOKEN_KEY)
            .remove(REFRESH_TOKEN_KEY)
            .apply()
    }
}
