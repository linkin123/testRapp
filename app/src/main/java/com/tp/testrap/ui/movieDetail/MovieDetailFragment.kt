package com.tp.testrap.ui.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tp.testrap.R
import com.tp.testrap.core.Resource
import com.tp.testrap.data.remote.RemoteVideosDataSource
import com.tp.testrap.databinding.FragmentMovieDetailBinding
import com.tp.testrap.presentation.VideoViewModel
import com.tp.testrap.presentation.VideoViewModelFactory
import com.tp.testrap.repository.RetrofitClient
import com.tp.testrap.repository.VideoRepositoryImpl
import com.tp.testrap.ui.activities.VideoActivity
import com.tp.testrap.ui.gone
import com.tp.testrap.ui.visible

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val videosViewmodel by viewModels<VideoViewModel> {
        VideoViewModelFactory(
            VideoRepositoryImpl(
                RemoteVideosDataSource(RetrofitClient.webService)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        setUpToolbar()
        onclicks()
        setUpImages()
    }


    private fun setUpImages() {
        with(binding) {
            imgMovie.paintImages(requireContext(), args.posterImageUrl)
            imgMovie.paintImages(requireContext(), args.backgroundIMageUrl)
            imgBackground.paintImages(requireContext(), args.backgroundIMageUrl)
            txtTitle.text = args.title
            txtOverview.text = args.overview
            txtLanguage.text = "Lenguaje : ${args.language}"
            txtRaiting.text = "${args.voteAverage} / ${args.voteCount} Reviews"
            txtRelease.text = "Released ${args.releaseDate}"
        }
    }

    private fun onclicks() {
        binding.imgMovie.setOnClickListener {
            videosViewmodel.getVideoById(args.id.toString()).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.visible()
                    }
                    is Resource.Success -> {
                        binding.progressBar.gone()
                        startActivity(
                            Intent(
                                activity,
                                VideoActivity::class.java
                            ).putExtra(VideoActivity.KEY, result.data[0].url)
                        )
                    }
                    is Resource.Failure -> {
                        binding.progressBar.gone()
                    }
                }

            }
        }

        binding.mToolbar.actionBar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpToolbar() {
        binding.mToolbar.actionBar.tvTitle.text = args.title
    }


    companion object {
        const val BASE_IMAGE = "https://image.tmdb.org/t/p/w500/"
    }


}

private fun ImageView.paintImages(context: Context, posterImageUrl: String) {
    Glide.with(context)
        .load("${MovieDetailFragment.BASE_IMAGE}$posterImageUrl")
        .centerCrop()
        .into(this)

}
