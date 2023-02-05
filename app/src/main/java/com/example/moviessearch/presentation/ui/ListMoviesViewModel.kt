package com.example.moviessearch.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviessearch.domain.entity.Film
import com.example.moviessearch.domain.interactor.IKinopoiskInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMoviesViewModel @Inject constructor(
    private val kinopoiskInteractor: IKinopoiskInteractor,
) : ViewModel() {

    private var _movieListLiveData = MutableLiveData<List<Film>?>()
    val movieListLiveData: LiveData<List<Film>?> = _movieListLiveData

    private var _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    fun getPopularMovies() {
        viewModelScope.launch {
            kinopoiskInteractor.getMoviesList("TOP_100_POPULAR_FILMS").onSuccess {
                _movieListLiveData.value = it.films
            }.onFailure {
                _movieListLiveData.value = null
               _errorLiveData.value = it
            }
        }
    }
}