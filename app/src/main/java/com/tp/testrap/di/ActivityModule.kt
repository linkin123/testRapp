package com.tp.testrap.di

import com.tp.testrap.data.local.LocalMovieDataSource
import com.tp.testrap.data.local.LocalMovieDataSourceImpl
import com.tp.testrap.data.remote.RemoteMovieDataSource
import com.tp.testrap.data.remote.RemoteMovieDataSourceImpl
import com.tp.testrap.data.remote.RemoteVideosDataSource
import com.tp.testrap.data.remote.RemoteVideosDataSourceImpl
import com.tp.testrap.repository.MovieRepository
import com.tp.testrap.repository.MovieRepositoryImpl
import com.tp.testrap.repository.VideoRepository
import com.tp.testrap.repository.VideoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    /*api movies */

    @Binds
    abstract fun bindMovieRepositoryImpl(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindRemoteMovieDataSourceImpl(remoteMovieDataSourceImpl: RemoteMovieDataSourceImpl): RemoteMovieDataSource

    @Binds
    abstract fun bindLocalMovieDataSourceImpl(localMovieDataSourceImpl: LocalMovieDataSourceImpl): LocalMovieDataSource


    /*api videos */

    @Binds
    abstract fun bindVideoRepositoryImpl(videoRepositoryImpl: VideoRepositoryImpl): VideoRepository

    @Binds
    abstract fun bindRemoteVideosDataSourceImpl(remoteVideosDataSourceImpl: RemoteVideosDataSourceImpl): RemoteVideosDataSource


}