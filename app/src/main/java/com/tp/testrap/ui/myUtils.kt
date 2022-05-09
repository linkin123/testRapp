package com.tp.testrap.ui

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tp.testrap.R
import com.tp.testrap.application.AppConstants
import com.tp.testrap.data.model.Movie
import com.tp.testrap.ui.movie.adapters.GenericAdapter
import com.tp.testrap.ui.movie.adapters.MoviesListAdapter
import com.tp.testrap.ui.movie.adapters.OnOption
import com.tp.testrap.ui.movie.adapters.TypeAdapter

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}


fun ImageView.loadImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        Log.d("TAGGGGGG", "url : $url")
        Glide.with(context).load(url).placeholder(R.drawable.rapiiiii).into(this)
    }
}

@BindingAdapter("imageURL")
fun ImageView.loadImage2(url: String?) {
    if (!url.isNullOrEmpty()) {
        Log.d("TAGGGGGG", "url : $url")
        Glide.with(context).load("${AppConstants.BASE_IMAGE}${url}")
            .placeholder(R.drawable.rapiiiii).into(this)
    }
}


@BindingAdapter("list_adapter", "type", "option")
fun RecyclerView.setAdapter(
    list: List<Movie>?, type: TypeAdapter, option: OnOption
) {

    if (!list.isNullOrEmpty()) {
        val mAdapter = MoviesListAdapter(option)
        mAdapter.submitList(list)
        this.adapter = GenericAdapter(mAdapter, type)
    }

}