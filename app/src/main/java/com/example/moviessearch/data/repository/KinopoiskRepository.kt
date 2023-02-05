package com.example.moviessearch.data.repository


import com.example.moviessearch.data.network.KinopoiskApi
import com.example.moviessearch.domain.entity.Film
import com.example.moviessearch.domain.entity.Movies
import javax.inject.Inject

class KinopoiskRepository
@Inject constructor(private val api: KinopoiskApi) : IKinopoiskRepository {
    override suspend fun getMoviesList(type: String): Result<Movies> {
        return api.getMoviesList(type)
    }

    override suspend fun getFilmById(id: String): Result<Film> {
        return api.getFilmById(id)
    }

}