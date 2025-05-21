package com.tcc.money.network.api

import com.tcc.money.data.models.Goals
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

interface GoalsApi {
    @GET("api/goals/findAll")
    fun findAll(): Call<ResponseBody>

    @GET("api/goals/find/{uuid}")
    fun findByUUID(
        @Path("uuid") uuid: UUID
    ): Call<ResponseBody>

    @POST("api/goals/save")
    fun save(
        @Body goals: Goals
    ): Call<ResponseBody>

    @DELETE("api/goals/delete/{uuid}")
    fun delete(
        @Path("uuid") uuid: UUID
    )

    @PUT("api/goals/update/{uuid}")
    fun update(
        @Path("uuid") uuid: UUID,
        @Body goals: Goals
    ): Call<ResponseBody>
}
