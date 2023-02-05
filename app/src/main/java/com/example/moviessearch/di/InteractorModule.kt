package com.example.moviessearch.di

import com.example.moviessearch.domain.interactor.IKinopoiskInteractor
import com.example.moviessearch.domain.interactor.KinopoiskInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorModule {

    @Singleton
    @Binds
    abstract fun kinopoiskInteractor(KinopoiskInteractor: KinopoiskInteractor): IKinopoiskInteractor

}