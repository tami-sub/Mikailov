package com.example.moviessearch.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviessearch.R
import com.example.moviessearch.databinding.FragmentMovieBinding
import com.example.moviessearch.domain.entity.Country
import com.example.moviessearch.domain.entity.Genre
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: FragmentMovieBinding
    private val args: MovieFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val supportActionBar = (activity as AppCompatActivity?)!!.supportActionBar!!
        supportActionBar.title = ""
        supportActionBar.setHomeAsUpIndicator(R.drawable.back_arrow)
        binding = FragmentMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieInfo(args.filmId)
        viewModel.movieInfoLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                Glide.with(root)
                    .load(it?.posterUrl)
                    .into(poster)
                title.text = it?.nameRu
                description.text = it?.description

                genre.text = movieInfoStringBuilder(genre.text.toString(), it?.genres)
                counrty.text = movieInfoStringBuilder(counrty.text.toString(), it?.countries)
            }
        }
    }

    private fun movieInfoStringBuilder(str: String, list: List<Any>?): CharSequence? {
        var allString = str
        if (list != null) {
            for (i in list) {
                if (i is Genre) {
                    allString += " ${i.genre},"
                }
                if (i is Country) {
                    allString += " ${i.country},"
                }
            }
        }
        return allString.dropLast(1)
    }

    companion object {
        fun newInstance() = MovieFragment()
    }
}