package com.example.studyapp.di

import com.example.studyapp.data.repository.OfflineUserRepository
import com.example.studyapp.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        repository: OfflineUserRepository
    ): UserRepository
}
