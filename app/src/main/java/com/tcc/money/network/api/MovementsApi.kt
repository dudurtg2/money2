package com.tcc.money.network.api

import com.tcc.money.data.models.Movements
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface MovementsApi {
    @GET("api/movements/findAll")
    fun findAll(): Call<ResponseBody>

    @GET("api/movements/find/{uuid}")
    fun findByUUID(
        @Path("uuid") uuid: UUID
    ): Call<ResponseBody>

    @POST("api/movements/create")
    fun save(
        @Body movements: Movements
    ): Call<ResponseBody>

    @DELETE("api/movements/delete/{uuid}")
    fun delete(
        @Path("uuid") uuid: UUID
    )

    @PUT("api/movements/update/{uuid}")
    fun update(
        @Path("uuid") uuid: UUID,
        @Body movements: Movements
    ): Call<ResponseBody>
}

