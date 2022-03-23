package com.tp.testrap.ui.movieDetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tp.testrap.R
import com.tp.testrap.application.AppConstants.BASE_IMAGE
import com.tp.testrap.core.Resource
import com.tp.testrap.databinding.FragmentMovieDetailAnimBinding
import com.tp.testrap.presentation.VideoViewModel
import com.tp.testrap.ui.activities.VideoActivity
import com.tp.testrap.ui.gone
import com.tp.testrap.ui.loadImage
import com.tp.testrap.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail_anim), View.OnClickListener {

    private lateinit var binding: FragmentMovieDetailAnimBinding
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val videosViewmodel by viewModels<VideoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailAnimBinding.bind(view)
        onclicks()
        setUpImages()
    }


    private fun setUpImages() {
        with(binding) {
            imgMovie.loadImage("${BASE_IMAGE}${args.posterImageUrl}")
            imgBackground.loadImage("${BASE_IMAGE}${args.backgroundIMageUrl}")
            toolbar.title = args.title
            contentLayout.txtOverview.text = args.overview
            txtLanguage.text = "Lenguaje : ${args.language}"
            txtRaiting.text = "${args.voteAverage} / ${args.voteCount} Reviews"
            txtRelease.text = "Released ${args.releaseDate}"
        }
    }

    private fun onclicks() {
        binding.apply {
            imgMovie.setOnClickListener(this@MovieDetailFragment)
            btnSeeTrailer.setOnClickListener(this@MovieDetailFragment)
            imgBackground.setOnClickListener(this@MovieDetailFragment)
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }


    override fun onClick(p0: View?) {
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
                        ).apply {
                            putExtras(Bundle().apply {
                                putString(VideoActivity.KEY, result.data[0].url)
                                putString(VideoActivity.NAME, args.title)
                                putString(VideoActivity.SITE, result.data[0].site)
                                putString(VideoActivity.TYPE, result.data[0].type)
                            })
                        }
                    )
                }
                is Resource.Failure -> {
                    binding.progressBar.gone()
                }
            }
        }
    }
}
