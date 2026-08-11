package com.example.studyapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserApiModel(
    val id: Long? = null,
    val name: String,
    val email: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    val created_at: String? = null
)
