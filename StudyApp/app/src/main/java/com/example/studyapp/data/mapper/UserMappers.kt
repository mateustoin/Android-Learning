package com.example.studyapp.data.mapper

import com.example.studyapp.data.local.entity.UserEntity
import com.example.studyapp.data.model.UserApiModel
import com.example.studyapp.domain.model.User

// Room format (entity) to User domain
fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        created_at = created_at
    )
}

fun User.toApiModel(): UserApiModel {
    return UserApiModel(
        id = id,
        name = name,
        email = email,
        created_at = created_at
    )
}

// Domain format to Room (entity)
fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        created_at = created_at
    )
}

// API Model (Retrofit) to Entity (Room format)
fun UserApiModel.toEntity(): UserEntity {
    return UserEntity(
        id = id?.toLong() ?: 0L,
        name = name,
        email = email,
        created_at = created_at
    )
}

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        created_at = created_at
    )
}
