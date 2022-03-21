package com.tp.testrap.ui.movie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tp.testrap.core.BaseConcatHolder
import com.tp.testrap.databinding.PopularMovieRowBinding
import com.tp.testrap.databinding.TopRatedMoviesRowBinding
import com.tp.testrap.databinding.UpcomingMovieRowBinding

enum class TypeAdapter { POPULAR, TOP_RAITED, UP_COMING }

class GenericAdapter(private val moviesAdapter: MoviesListAdapter, private val type: TypeAdapter) :
    RecyclerView.Adapter<BaseConcatHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {

        return when (type) {
            TypeAdapter.POPULAR -> {
                val itemBinding =
                    PopularMovieRowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                PopularMovieViewHolder(itemBinding)
            }
            TypeAdapter.TOP_RAITED -> {
                val itemBinding =
                    TopRatedMoviesRowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TopRatedViewHolder(itemBinding)
            }
            TypeAdapter.UP_COMING -> {
                val itemBinding =
                    UpcomingMovieRowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                UpcomingViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder) {
            is PopularMovieViewHolder -> holder.bind(moviesAdapter)
            is TopRatedViewHolder -> holder.bind(moviesAdapter)
            is UpcomingViewHolder -> holder.bind(moviesAdapter)
        }
    }

    override fun getItemCount() = 1

    private inner class PopularMovieViewHolder(val binding: PopularMovieRowBinding) :
        BaseConcatHolder<MoviesListAdapter>(binding.root) {
        override fun bind(adapter: MoviesListAdapter) {
            binding.rvPopularMovies.adapter = adapter
        }

    }

    private inner class TopRatedViewHolder(val binding: TopRatedMoviesRowBinding) :
        BaseConcatHolder<MoviesListAdapter>(binding.root) {
        override fun bind(adapter: MoviesListAdapter) {
            binding.rvTopRatedMovies.adapter = adapter
        }
    }

    private inner class UpcomingViewHolder(val binding: UpcomingMovieRowBinding) :
        BaseConcatHolder<MoviesListAdapter>(binding.root) {
        override fun bind(adapter: MoviesListAdapter) {
            binding.rvUpcomingMovies.adapter = adapter
        }
    }

}