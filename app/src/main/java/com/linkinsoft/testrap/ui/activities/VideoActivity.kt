package com.linkinsoft.testrap.ui.activities

import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.linkinsoft.testrap.application.YoutubeConfig
import com.linkinsoft.testrap.databinding.ActivityVideBinding

class VideoActivity : YouTubeBaseActivity() {
    companion object {
        val TYPE = "TYPE"
        val SITE = "SITE"
        val NAME = "NAME"
        const val KEY = "ID_KEY"
    }

    private lateinit var binding: ActivityVideBinding
    private lateinit var mOnListener: YouTubePlayer.OnInitializedListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntents()
        initVideoPlayer()
        onclicks()
    }

    private fun onclicks() {
        binding.mToolbar.actionBar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun initVideoPlayer() {
        binding.videoPlayer.initialize(YoutubeConfig.secret, mOnListener)
    }

    private fun getIntents() {
        setUpvideo(intent.extras?.getString(KEY))
        setUpInfo(intent.extras)
    }


    private fun setUpInfo(extras: Bundle?) {
        with(binding){
            mToolbar.actionBar.tvTitle.text = extras?.getString(NAME)
        }
    }

    private fun setUpvideo(key: String?) {
        mOnListener = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                youtubePlayer: YouTubePlayer?,
                p2: Boolean
            ) {
                youtubePlayer?.loadVideo(key)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                Log.d("TAG", "error al reproducir el video ")
            }
        }
        binding.videoPlayer.initialize(YoutubeConfig.secret, mOnListener)
    }

}