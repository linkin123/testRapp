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
import com.tp.testrap.core.CurrentConn
import com.tp.testrap.core.InternetCheck
import com.tp.testrap.core.InternetConnection
import com.tp.testrap.core.Resource
import com.tp.testrap.data.model.Movie
import com.tp.testrap.data.model.MovieList
import com.tp.testrap.data.model.MovieListAd
import com.tp.testrap.databinding.FragmentMovieBinding
import com.tp.testrap.presentation.MovieViewModel
import com.tp.testrap.ui.gone
import com.tp.testrap.ui.movie.adapters.OnOption
import com.tp.testrap.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie), OnOption {
    private lateinit var binding: FragmentMovieBinding

    private val viewmodel by viewModels<MovieViewModel>()

    private lateinit var concatAdapter: ConcatAdapter

    private var popularList = listOf<Movie>()
    private var topRaitedList = listOf<Movie>()
    private var upComingList = listOf<Movie>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        binding.option = this
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
                    conexionAvaibled(popularList, topRaitedList, upComingList,
                        isNetworkAvaible = {
                            binding.tvNotConnection.gone()
                            binding.rvMovies1.visible()
                            binding.rvMovies2.visible()
                            binding.rvMovies3.visible()
                            submitList(popularList, topRaitedList, upComingList)
                        }, isNetworkDisabled = {
                            binding.tvNotConnection.visible()
                            binding.rvMovies1.gone()
                            binding.rvMovies2.gone()
                            binding.rvMovies3.gone()
                        })
                }
                is Resource.Failure -> {
                    binding.progressBar.gone()
                    Snackbar.make(binding.root, "${result.exception}", Snackbar.LENGTH_SHORT)
                    Log.d("LiveData", "${result.exception}")
                }
            }
        }
    }

    private fun conexionAvaibled(
        popularList: List<Movie>,
        topRaitedList: List<Movie>,
        upComingList: List<Movie>,
        isNetworkAvaible: () -> Unit,
        isNetworkDisabled: () -> Unit,
    ) {
        if (popularList.isNullOrEmpty() && topRaitedList.isNullOrEmpty() && upComingList.isNullOrEmpty() && !InternetConnection.check(
                requireContext()
            ).isConected()
        ) {
            isNetworkDisabled.invoke()
        } else {
            isNetworkAvaible.invoke()
        }
    }

    private fun submitList(
        popularList: List<Movie>,
        topRaitedList: List<Movie>,
        upComingList: List<Movie>,
    ) {
        binding.list = MovieListAd(
            listOf(
                MovieList(popularList),
                MovieList(topRaitedList),
                MovieList(upComingList)
            )
        )
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

private fun CurrentConn.isConected(): Boolean {
    return (this == CurrentConn.IS_WIFI || this == CurrentConn.IS_MOBILE)
}

private fun List<Movie>.getFilterWith(text: String): List<Movie> {
    return this.filter { it.title.contains(text, true) }
}
