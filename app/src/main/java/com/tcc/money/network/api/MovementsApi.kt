package com.tcc.money.network.api

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovementsApi {
    @GET("api/movements/findAll")
     suspend fun findAll(): Response<ResponseBody>

    @GET("api/movements/find/{id}")
     suspend fun findById(@Path("id") id: Long): Response<ResponseBody>

    @POST("api/movements/save")
     suspend fun save(@Body movements: Movements): Response<ResponseBody>
}