package com.example.moviessearch.data.repository

import com.example.moviessearch.domain.entity.Film
import com.example.moviessearch.domain.entity.Movies

interface IKinopoiskRepository {
    suspend fun getMoviesList(type: String): Result<Movies>
    suspend fun getFilmById(id: String): Result<Film>
}
