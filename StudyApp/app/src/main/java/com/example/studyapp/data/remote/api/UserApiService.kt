package com.example.studyapp.data.remote.api

import com.example.studyapp.data.remote.model.UserApiModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface UserApiService {
    @GET("usuarios_fake?select=*")
    suspend fun getAllUsers(): List<UserApiModel>

    @DELETE("usuarios_fake")
    suspend fun deleteUser(
        @Query("id") idFilter: String
    ): Response<Unit>

    @POST("usuarios_fake")
    suspend fun addUser(
        @Body newUser: UserApiModel
    ): Response<Unit>
}
