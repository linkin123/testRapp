package com.tp.testrap.ui.movie.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tp.testrap.R
import com.tp.testrap.application.AppConstants.DIALOG
import com.tp.testrap.application.AppConstants.ELSE
import com.tp.testrap.application.AppConstants.EN
import com.tp.testrap.application.AppConstants.ENGLISH
import com.tp.testrap.application.AppConstants.ES
import com.tp.testrap.application.AppConstants.FR
import com.tp.testrap.application.AppConstants.FRENCH
import com.tp.testrap.application.AppConstants.JA
import com.tp.testrap.application.AppConstants.JAPANESSE
import com.tp.testrap.application.AppConstants.KO
import com.tp.testrap.application.AppConstants.KOREAN
import com.tp.testrap.application.AppConstants.SPANISH
import com.tp.testrap.core.Resource
import com.tp.testrap.data.model.Movie
import com.tp.testrap.databinding.FragmentMovieBinding
import com.tp.testrap.presentation.MovieViewModel
import com.tp.testrap.ui.gone
import com.tp.testrap.ui.movie.adapters.GenericAdapter
import com.tp.testrap.ui.movie.adapters.MoviesListAdapter
import com.tp.testrap.ui.movie.adapters.OnOption
import com.tp.testrap.ui.movie.adapters.TypeAdapter
import com.tp.testrap.ui.utils.CallbackItem
import com.tp.testrap.ui.utils.RecyclerDialogFragment
import com.tp.testrap.ui.visible
import dagger.hilt.android.AndroidEntryPoint


enum class TypeSearch {
    DATE, LANGUAGE
}

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie), OnOption {
    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel>()
    private lateinit var concatAdapter: ConcatAdapter
    private var popularAdapter = MoviesListAdapter(this)
    private var topRaitedAdapter = MoviesListAdapter(this)
    private var upComingAdapter = MoviesListAdapter(this)
    private var recommendedFourYouAdapter = MoviesListAdapter(this)
    private var popularList = listOf<Movie>()
    private var topRaitedList = listOf<Movie>()
    private var recommendedForYouList = listOf<Movie>()
    private var upComingList = listOf<Movie>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        observers()
        onClicks()
    }

    private fun onClicks() {
        binding.include.search.addTextChangedListener { edit ->
            if (!edit.isNullOrEmpty()) {
                submitList(
                    popularList.getFilterWith(edit.toString()),
                    topRaitedList.getFilterWith(edit.toString()),
                    upComingList.getFilterWith(edit.toString()),
                    recommendedForYouList
                )
            } else {
                submitList(
                    popularList,
                    topRaitedList,
                    upComingList,
                    recommendedForYouList
                )
            }
        }

        binding.rgSelector.setOnCheckedChangeListener { _, _ ->
            if (binding.rbLanguage.isChecked) {
                filterByLanguage()
            } else if (binding.rbYear.isChecked) {
                filterByDate()
            }
        }
    }

    private fun filterByDate() {
        showDialogItems(recommendedForYouList.getDates(), TypeSearch.DATE)
    }

    private fun filterByLanguage() {
        showDialogItems(recommendedForYouList.getLanguages(), TypeSearch.LANGUAGE)
    }

    private fun showDialogItems(list: List<String>, typeSearch: TypeSearch) {
        RecyclerDialogFragment.newInstance(
            list, callback = object : CallbackItem {
                override fun onItemSelected(position: Int) {
                    recommendedFourYouAdapter.submitList(
                        recommendedForYouList.getFilterByTerm(
                            list[position],
                            typeSearch
                        )
                    )
                }
            }).show(parentFragmentManager, DIALOG)

    }

    private fun observers() {
        concatAdapter = ConcatAdapter()
        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visible()
                }
                is Resource.Success -> {
                    binding.progressBar.gone()
                    with(result.data) {
                        popularList = first.results
                        topRaitedList = second.results
                        recommendedForYouList = second.results.take(6)
                        upComingList = third.results
                    }
                    submitList(popularList, topRaitedList, upComingList, recommendedForYouList)

                    concatAdapter.apply {
                        addAdapter(
                            0, GenericAdapter(upComingAdapter, TypeAdapter.UP_COMING)
                        )
                        addAdapter(
                            1, GenericAdapter(topRaitedAdapter, TypeAdapter.TOP_RAITED)
                        )
                        addAdapter(
                            2, GenericAdapter(popularAdapter, TypeAdapter.POPULAR)
                        )
                    }
                    binding.rvMovies.adapter = concatAdapter
                    with(binding.rvMoviesRecommemdedForYou) {
                        layoutManager = GridLayoutManager(context, 3)
                        adapter = recommendedFourYouAdapter
                    }

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
        recommendedForYouList: List<Movie>,
    ) {
        popularAdapter.submitList(popularList)
        topRaitedAdapter.submitList(topRaitedList)
        upComingAdapter.submitList(upComingList)
        recommendedFourYouAdapter.submitList(recommendedForYouList)
    }

    override fun click(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.id,
            movie.poster_path,
            movie.backdrop_path ?: "",
            movie.vote_average,
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )

        findNavController().navigate(action)

    }

}

private fun List<Movie>.getFilterByTerm(term: String, typeSearch: TypeSearch): List<Movie> {
    return when (typeSearch) {
        TypeSearch.DATE -> {
            this.filter {
                it.release_date.substring(0, 4) == term
            }
        }
        TypeSearch.LANGUAGE -> {
            this.filter {
                it.original_language.completeString() == term
            }
        }
    }
}

private fun List<Movie>.getDates(): List<String> {
    return this.map {
        it.release_date.substring(0, 4)
    }.distinct()
}

private fun List<Movie>.getLanguages(): List<String> {
    return this.map {
        it.original_language.completeString()
    }.distinct()
}

private fun String.completeString(): String {
    return when (this) {
        EN -> ENGLISH
        KO -> KOREAN
        ES -> SPANISH
        FR -> FRENCH
        JA -> JAPANESSE
        else -> ELSE
    }
}

private fun List<Movie>.getFilterWith(text: String): List<Movie> {
    return this.filter { it.title.contains(text, true) }
}