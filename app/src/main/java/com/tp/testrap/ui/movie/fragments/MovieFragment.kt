package com.tp.testrap.ui.movie.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
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
import com.tp.testrap.ui.movie.adapters.MoviesListAdapter
import com.tp.testrap.ui.movie.adapters.OnOption
import com.tp.testrap.ui.movie.adapters.TypeAdapter
import com.tp.testrap.ui.visible

class MovieFragment : Fragment(R.layout.fragment_movie), OnOption {
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
    private var popularAdapter = MoviesListAdapter(this)
    private var topRaitedAdapter = MoviesListAdapter(this)
    private var upComingAdapter = MoviesListAdapter(this)

    private var popularList = listOf<Movie>()
    private var topRaitedList = listOf<Movie>()
    private var upComingList = listOf<Movie>()

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

        binding.include.search.addTextChangedListener { edit ->
            if (!edit.isNullOrEmpty()) {
                submitList(
                    popularList.getFilterWith(edit.toString()),
                    topRaitedList.getFilterWith(edit.toString()),
                    upComingList.getFilterWith(edit.toString())
                )
            } else {
                submitList(
                    popularList,
                    topRaitedList,
                    upComingList
                )
            }
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
                    with(result.data) {
                        popularList = first.results
                        topRaitedList = second.results
                        upComingList = third.results
                    }
                    submitList(popularList, topRaitedList, upComingList)

                    concatAdapter.apply {
                        addAdapter(
                            0, GenericAdapter(popularAdapter, TypeAdapter.POPULAR)
                        )
                        addAdapter(
                            1, GenericAdapter(topRaitedAdapter, TypeAdapter.TOP_RAITED)
                        )
                        addAdapter(
                            2, GenericAdapter(upComingAdapter, TypeAdapter.UP_COMING)
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

    private fun submitList(
        popularList: List<Movie>,
        topRaitedList: List<Movie>,
        upComingList: List<Movie>,
    ) {
        popularAdapter.submitList(popularList)
        topRaitedAdapter.submitList(topRaitedList)
        upComingAdapter.submitList(upComingList)
    }

    override fun click(movie: Movie) {
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

private fun List<Movie>.getFilterWith(text: String): List<Movie> {
    return this.filter { it.title.contains(text, true) }
}
