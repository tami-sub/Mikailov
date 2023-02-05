package com.example.moviessearch.domain.interactor

import com.example.moviessearch.domain.entity.Film
import com.example.moviessearch.domain.entity.Movies

interface IKinopoiskInteractor {
    suspend fun getMoviesList(type: String): Result<Movies>
    suspend fun getFilmById(id: String): Result<Film>
}