package com.linkinsoft.testrap.di

import com.linkinsoft.testrap.data.local.LocalMovieDataSource
import com.linkinsoft.testrap.data.local.LocalMovieDataSourceImpl
import com.linkinsoft.testrap.data.remote.RemoteMovieDataSource
import com.linkinsoft.testrap.data.remote.RemoteMovieDataSourceImpl
import com.linkinsoft.testrap.data.remote.RemoteVideosDataSource
import com.linkinsoft.testrap.data.remote.RemoteVideosDataSourceImpl
import com.linkinsoft.testrap.repository.MovieRepository
import com.linkinsoft.testrap.repository.MovieRepositoryImpl
import com.linkinsoft.testrap.repository.VideoRepository
import com.linkinsoft.testrap.repository.VideoRepositoryImpl
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