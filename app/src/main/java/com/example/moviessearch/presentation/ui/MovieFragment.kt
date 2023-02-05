package com.example.moviessearch.presentation.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
        viewModel.getMovieInfo(args.filmId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            layoutErrorMovie.refresh.setOnClickListener {
                viewModel.getMovieInfo(args.filmId)
                showMainContent()
            }
        }
        showMainContent()
    }

    private fun showMainContent() {
        viewModel.movieInfoLiveData.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.poster.visibility = View.GONE
                binding.noMovieConnectivity.visibility = View.VISIBLE
            } else {
                binding.layoutErrorMovie.error.text = ""
                binding.poster.visibility = View.VISIBLE
                binding.noMovieConnectivity.visibility = View.GONE
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
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            if (checkInternetConnectivity()) {
                binding.layoutErrorMovie.error.text = it.message
            } else {
                binding.layoutErrorMovie.error.text = "Проблемы с интернет подключением"
            }
        }
        val isConnected = checkInternetConnectivity()
        with(binding) {
            if (!isConnected) {
                poster.visibility = View.GONE
                noMovieConnectivity.visibility = View.VISIBLE
            } else {
                poster.visibility = View.VISIBLE
                noMovieConnectivity.visibility = View.GONE
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

    private fun checkInternetConnectivity(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
    }

    companion object {
        fun newInstance() = MovieFragment()
    }
}