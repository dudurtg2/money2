
package com.tcc.money.network.retrofit
// NetworkModule.kt (Hilt)

import android.content.Context
import com.tcc.money.data.services.AuthenticateService
import com.tcc.money.network.api.CoinsApi
import com.tcc.money.network.api.GoalsApi
import com.tcc.money.network.api.MovementsApi
import com.tcc.money.network.api.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://10.0.0.150:8080/"

    // 1) Interceptor que injeta o token no header
    @Provides
    @Singleton
    fun provideAuthInterceptor(
        @ApplicationContext context: Context
    ): Interceptor = Interceptor { chain ->
        val token = AuthenticateService.getToken(context)
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        chain.proceed(request)
    }

    // 2) Logging HTTP (opcional)
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    // 3) OkHttpClient com interceptores
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // 4) Retrofit genérico
    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 5) CoinsApi
    @Provides
    @Singleton
    fun provideCoinsApi(retrofit: Retrofit): CoinsApi =
        retrofit.create(CoinsApi::class.java)

    // 6) GoalsApi
    @Provides
    @Singleton
    fun provideGoalsApi(retrofit: Retrofit): GoalsApi =
        retrofit.create(GoalsApi::class.java)

    // 7) MovementsApi
    @Provides
    @Singleton
    fun provideMovementsApi(retrofit: Retrofit): MovementsApi =
        retrofit.create(MovementsApi::class.java)

    // 8) UsersApi
    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UsersApi =
        retrofit.create(UsersApi::class.java)
}
