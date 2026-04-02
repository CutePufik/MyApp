package com.example.myapplication.di

import com.example.myapplication.data.repository.AppDetailsRepositoryImpl
import com.example.myapplication.domain.repository.AppDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDetailsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppDetailsRepository(impl: AppDetailsRepositoryImpl): AppDetailsRepository
}
