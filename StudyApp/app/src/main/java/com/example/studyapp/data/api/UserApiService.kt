package com.example.studyapp.data.api

import com.example.studyapp.data.model.UserApiModel
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
    ): retrofit2.Response<Unit>

    @POST("usuarios_fake")
    suspend fun addUser(
        @Body newUser: UserApiModel
    ): retrofit2.Response<Unit>//UserApiModel
}
