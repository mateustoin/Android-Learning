package com.example.studyapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val id: Long? = null,
    val remoteId: String? = null,
    val name: String,
    val email: String? = null,
    val avatarUrl: String? = null,
    val created_at: String? = null,
)
