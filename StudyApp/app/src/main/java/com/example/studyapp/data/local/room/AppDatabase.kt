package com.example.studyapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studyapp.data.local.room.dao.UserDao
import com.example.studyapp.data.local.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}