package com.example.moviessearch.di

import com.example.moviessearch.data.repository.IKinopoiskRepository
import com.example.moviessearch.data.repository.KinopoiskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun kinopoiskRepository(KinopoiskRepository: KinopoiskRepository): IKinopoiskRepository
}