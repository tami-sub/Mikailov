package com.example.moviessearch.data.network

import com.example.moviessearch.domain.entity.Movies
import com.example.moviessearch.domain.entity.Film
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("api/v2.2/films/top")
    suspend fun getMoviesList(
        @Query("type") type: String
    ): Result<Movies>

    @GET("api/v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("id") id: String
    ): Result<Film>
}