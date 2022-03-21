package com.tp.testrap.ui

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.tp.testrap.R

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

@BindingAdapter("imageURL")
fun ImageView.loadImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        Log.d("TAGGGGGG", "url : $url")
        Glide.with(context).load(url).placeholder(R.drawable.rapiiiii).into(this)
    }
}

fun ImageView.loadImage2(url: String?) {
    if (!url.isNullOrEmpty()) {
        Log.d("TAGGGGGG", "url : $url")
        Glide.with(context).load(url).placeholder(R.drawable.rapiiiii).into(this)
    }
}
