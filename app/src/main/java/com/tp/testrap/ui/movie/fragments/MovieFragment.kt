package com.tp.testrap.ui.movie.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.snackbar.Snackbar
import com.tp.testrap.R
import com.tp.testrap.core.Resource
import com.tp.testrap.data.local.AppDatabase
import com.tp.testrap.data.local.LocalMovieDataSource
import com.tp.testrap.data.model.Movie
import com.tp.testrap.data.remote.RemoteMovieDataSource
import com.tp.testrap.databinding.FragmentMovieBinding
import com.tp.testrap.presentation.MovieViewModel
import com.tp.testrap.presentation.MovieViewModelFactory
import com.tp.testrap.repository.MovieRepositoryImpl
import com.tp.testrap.repository.RetrofitClient
import com.tp.testrap.ui.gone
import com.tp.testrap.ui.movie.adapters.GenericAdapter
import com.tp.testrap.ui.movie.adapters.MovieAdapter
import com.tp.testrap.ui.movie.adapters.TypeAdapter
import com.tp.testrap.ui.visible

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClikListener {


    private lateinit var binding: FragmentMovieBinding
    private val viewmodel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webService),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        setUpToolbar()
        observers()
        onclicks()
    }

    private fun onclicks() {
        binding.mToolbar.actionBar.ivBack.setOnClickListener {
            activity?.finish()
        }
    }

    private fun setUpToolbar() {
        binding.mToolbar.actionBar.tvTitle.text = "Movies"
    }

    private fun observers() {
        concatAdapter = ConcatAdapter()

        viewmodel.fetchMainScreenMovies().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visible()
                }
                is Resource.Success -> {
                    binding.progressBar.gone()
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            GenericAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                ), TypeAdapter.POPULAR
                            )
                        )
                        addAdapter(
                            1,
                            GenericAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@MovieFragment
                                ), TypeAdapter.TOP_RAITED
                            )
                        )
                        addAdapter(
                            2,
                            GenericAdapter(
                                MovieAdapter(
                                    result.data.third.results,
                                    this@MovieFragment
                                ), TypeAdapter.UP_COMING
                            )
                        )
                    }
                    binding.rvMovies.adapter = concatAdapter

                }
                is Resource.Failure -> {
                    binding.progressBar.gone()
                    Snackbar.make(binding.root, "${result.exception}", Snackbar.LENGTH_SHORT)
                    Log.d("LiveData", "${result.exception}")
                }
            }
        }

    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.id,
            movie.poster_path,
            movie.backdrop_path ?: "",
            movie.vote_avergage.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )

        findNavController().navigate(action)
    }


}