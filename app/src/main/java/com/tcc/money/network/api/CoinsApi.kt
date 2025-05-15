package com.tcc.money.network.api

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.models.Coins
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface CoinsApi {
    @GET("api/coins/findAll")
     suspend fun findAll(): Response<ResponseBody>

    @GET("api/coins/find/{uuid}")
     suspend fun findByUUID(@Path("uuid") uuid: UUID): Response<ResponseBody>

    @POST("api/coins/save")
     suspend fun save(@Body coins: Coins): Response<ResponseBody>
}