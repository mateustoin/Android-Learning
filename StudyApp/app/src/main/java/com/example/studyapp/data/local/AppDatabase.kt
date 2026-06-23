package com.example.studyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studyapp.data.local.dao.UserDao
import com.example.studyapp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
