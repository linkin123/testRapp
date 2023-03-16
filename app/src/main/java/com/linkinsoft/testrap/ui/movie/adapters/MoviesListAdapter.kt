package com.linkinsoft.testrap.ui.movie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.linkinsoft.testrap.core.BaseViewHolder
import com.linkinsoft.testrap.data.model.Movie
import com.linkinsoft.testrap.databinding.MovieItemBinding

class MoviesListAdapter( private val onOption: OnOption) :
    ListAdapter<Movie, BaseViewHolder<*>>(ItemMoviesCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MoviesBiewHolder(itemBinding)


        itemBinding.imgMovie.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            onOption.click(getItem(position))
        }
        return holder

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MoviesBiewHolder -> holder.bind(getItem(position))
        }
    }


    private inner class MoviesBiewHolder(val binding: MovieItemBinding) :
        BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie) {
            with(binding) {
                movie = item
                executePendingBindings()
            }
        }
    }
}

interface OnOption {
    fun click(movie: Movie)
}

class ItemMoviesCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(
        oldItem: Movie, newItem: Movie
    ): Boolean {
        return newItem == oldItem
    }

}