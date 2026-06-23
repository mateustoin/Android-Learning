package com.example.studyapp.domain.model

data class User(
    val id: Long? = null,
    val name: String,
    val email: String? = null,
    val created_at: String? = null,
)
