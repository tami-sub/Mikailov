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
class MovieViewModel @Inject constructor(
    private val kinopoiskInteractor: IKinopoiskInteractor,
) : ViewModel() {

    private var _movieInfoLiveData = MutableLiveData<Film?>()
    val movieInfoLiveData: LiveData<Film?> = _movieInfoLiveData

    private var _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    fun getMovieInfo(id: String) {
        viewModelScope.launch {
            kinopoiskInteractor.getFilmById(id).onSuccess {
                _movieInfoLiveData.value = it
            }.onFailure {
                _movieInfoLiveData.value = null
                _errorLiveData.value = it
            }
        }
    }
}