package com.example.studyapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val name: String,
    val email: String? = null,
//    @ColumnInfo(name = "created_at")
    val created_at: String? = null,
    val isSynced: Boolean = true
)
