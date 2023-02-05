package com.example.moviessearch.presentation

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviessearch.databinding.ListItemMovieBinding
import com.example.moviessearch.domain.entity.Film
import com.example.moviessearch.presentation.ui.ListMoviesFragmentDirections
import dagger.hilt.android.qualifiers.ApplicationContext

class MoviesAdapter(private val navController: NavController) :
    RecyclerView.Adapter<MoviesAdapter.WeatherViewHolder>() {

    private var movies = mutableListOf<Film>()

    class WeatherViewHolder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(film: Film) {
            binding.also { x ->
                x.name.text = film.nameRu
                x.type.text = getGenres(film)
                Glide.with(x.root)
                    .load(film.posterUrlPreview)
                    .into(x.imageView)
            }
        }

        private fun getGenres(film: Film): String {
            var allGenres = ""
            for ((count, i) in film.genres.withIndex()) {
                allGenres += " ${i.genre},"
                if (count == 1) break
            }
            allGenres += " (${film.year})"
            return allGenres.trim()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(inflater, parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        movies.getOrNull(position)?.let { movie ->
            holder.bind(movie)
        }
        holder.itemView.setOnClickListener {
            val action =
                ListMoviesFragmentDirections.actionListMoviesFragmentToMovieFragment(movies[position].filmId.toString())
            navController.navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(weatherList: List<Film>) {
        movies = weatherList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movies.size
}