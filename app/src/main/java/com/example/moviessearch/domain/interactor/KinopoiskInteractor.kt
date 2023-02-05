package com.example.moviessearch.domain.interactor

import com.example.moviessearch.data.repository.IKinopoiskRepository
import com.example.moviessearch.domain.entity.Film
import com.example.moviessearch.domain.entity.Movies
import javax.inject.Inject

class KinopoiskInteractor @Inject constructor(private val repository: IKinopoiskRepository) :
    IKinopoiskInteractor {
    override suspend fun getMoviesList(type: String): Result<Movies> {
        return repository.getMoviesList(type)
    }

    override suspend fun getFilmById(id: String): Result<Film> {
        return repository.getFilmById(id)
    }
}
