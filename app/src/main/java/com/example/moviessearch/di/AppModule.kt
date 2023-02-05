package com.example.moviessearch.di


import com.example.moviessearch.exception.ResultCallAdapterFactory
import com.example.moviessearch.data.network.CustomInterceptor
import com.example.moviessearch.data.network.KinopoiskApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): KinopoiskApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .client(loggingHttp())
            .build()
        return retrofit.create(KinopoiskApi::class.java)
    }

    @Singleton
    @Provides
    fun loggingHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(CustomInterceptor())
            .build()
    }
}