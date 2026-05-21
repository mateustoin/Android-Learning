package com.example.studyapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserApiModel(
    val id: Int? = null,
    val name: String,
    val email: String? = null,
    //val avatarUrl: String? = null,
    val created_at: String? = null
)