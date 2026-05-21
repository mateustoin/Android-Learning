package com.example.studyapp.data.api

import com.example.studyapp.data.model.UserApiModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST


interface UserApiService {
    @GET("usuarios_fake?select=*")
    suspend fun getAllUsers(): List<UserApiModel>

//    @GET("usuarios_fake?id=eq.$id")

//    @DELETE("usuarios_fake?id=$id")
//    suspend fun deleteUser(id: Int)

    @POST("users")
    suspend fun addUser(@Body newUser: UserApiModel): UserApiModel
}
