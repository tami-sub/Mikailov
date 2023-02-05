package com.example.moviessearch.presentation.ui

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviessearch.R
import com.example.moviessearch.databinding.FragmentListMoviesBinding
import com.example.moviessearch.presentation.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListMoviesFragment : Fragment() {

    private val viewModel: ListMoviesViewModel by viewModels()
    private lateinit var binding: FragmentListMoviesBinding
    private lateinit var movieListAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListMoviesBinding.inflate(layoutInflater)
        movieListAdapter = MoviesAdapter(findNavController())
        binding.recyclerView.adapter = movieListAdapter
        viewModel.getPopularMovies()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var supportActionBar = (activity as AppCompatActivity).supportActionBar!!
        supportActionBar.title = getString(R.string.popular)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        showMainContent()
        with(binding) {
            layoutError.refresh.setOnClickListener {
                viewModel.getPopularMovies()
                showMainContent()
            }
            favoriteBtn.setOnClickListener {
                Toast.makeText(activity,"Избранные в разработке", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showMainContent() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.recyclerView.visibility = View.GONE
                binding.noConnectivity.visibility = View.VISIBLE
            } else {
                movieListAdapter.setItems(it)
                binding.layoutError.error.text = ""
                binding.recyclerView.visibility = View.VISIBLE
                binding.noConnectivity.visibility = View.GONE
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            if (checkInternetConnectivity()) {
                binding.layoutError.error.text = it.message
            } else {
                binding.layoutError.error.text = "Проблемы с интернет подключением"
            }
        }
        val isConnected = checkInternetConnectivity()
        with(binding) {
            if (!isConnected) {
                recyclerView.visibility = View.GONE
                noConnectivity.visibility = View.VISIBLE
            } else {
                binding.recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerView.visibility = View.VISIBLE
                noConnectivity.visibility = View.GONE
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item: MenuItem = menu.findItem(R.id.search_bar)
        (activity as AppCompatActivity).supportActionBar!!.setTitleColor(Color.BLACK)
        item.setIcon(R.drawable.search2)
        item.isVisible = true
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

    private fun ActionBar.setTitleColor(color: Int) {
        val text = SpannableString(title ?: "")
        text.setSpan(ForegroundColorSpan(color), 0, text.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        title = text
    }

}