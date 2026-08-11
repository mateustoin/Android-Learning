package com.example.studyapp.data.mapper

import com.example.studyapp.data.local.room.entity.UserEntity
import com.example.studyapp.data.remote.model.UserApiModel
import com.example.studyapp.domain.model.User

// Room format (entity) to User domain
fun UserEntity.toUser(): User {
    return User(
        id = id,
        remoteId = remoteId,
        name = name,
        email = email,
        avatarUrl = avatarUrl,
        created_at = created_at
    )
}

fun User.toApiModel(): UserApiModel {
    return UserApiModel(
        id = remoteId?.toLongOrNull(),
        name = name,
        email = email,
        avatarUrl = avatarUrl,
        created_at = created_at
    )
}

// Domain format to Room (entity)
fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id ?: 0L,
        remoteId = remoteId,
        name = name,
        email = email,
        avatarUrl = avatarUrl,
        created_at = created_at
    )
}

// API Model (Retrofit) to Entity (Room format)
fun UserApiModel.toEntity(): UserEntity {
    return UserEntity(
        id = 0L, // Use 0 to let Room auto-generate if needed, or we might need to find by remoteId
        remoteId = id?.toString(),
        name = name,
        email = email,
        avatarUrl = avatarUrl,
        created_at = created_at,
        isSynced = true
    )
}
