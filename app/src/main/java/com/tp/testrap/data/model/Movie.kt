package com.tp.testrap.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Movie(
    val id: Int = -1,
    val adult: Boolean = false,
    val backdrop_path: String? = "",
    val original_title: String = "",
    val original_language: String = "",
    val overview: String = "",
    val popularity: Double = -1.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Float = 0F,
    val vote_count: Int = -1,
    val movie_type : String = ""
)

data class MovieList(
    val results: List<Movie> = listOf()
)


@Entity
data class MovieEntity(
    @PrimaryKey
    var id: Int = -1,
    @ColumnInfo(name = "adult")
    var adult: Boolean = false,
    @ColumnInfo(name = "backdrop_path")
    var backdrop_path: String = "",
    @ColumnInfo(name = "original_tittle")
    var original_tittle: String = "",
    @ColumnInfo(name = "original_languge")
    var original_languge: String = "",
    @ColumnInfo(name = "overview")
    var overview: String = "",
    @ColumnInfo(name = "popularity")
    var popularity: Double = -1.0,
    @ColumnInfo(name = "poster_path")
    var poster_path: String = "",
    @ColumnInfo(name = "release_date")
    var release_date: String = "",
    @ColumnInfo(name = "tittle")
    var tittle: String = "",
    @ColumnInfo(name = "video")
    var video: Boolean = false,
    @ColumnInfo(name = "vote_average")
    var vote_average: Float = 0F,
    @ColumnInfo(name = "vote_count")
    var vote_count: Int = -1,
    @ColumnInfo(name = "movie_type")
    var movie_type: String = ""
)

fun List<MovieEntity>.toMovieList() : MovieList{
    val resultList = mutableListOf<Movie>()
    this.forEach { movieEntity->
        resultList.add(movieEntity.toMovie())
    }
    return MovieList(resultList)
}

fun MovieEntity.toMovie() : Movie = Movie(
    this.id,
    this.adult,
    this.backdrop_path,
    this.original_tittle,
    this.original_languge,
    this.overview,
    this.popularity,
    this.poster_path,
    this.release_date,
    this.tittle,
    this.video,
    this.vote_average,
    this.vote_count,
    this.movie_type
)

fun Movie.toMovieEntity(movieType : String ) : MovieEntity = MovieEntity(
    this.id,
    this.adult,
    this.backdrop_path ?: "",
    this.original_title,
    this.original_language,
    this.overview,
    this.popularity,
    this.poster_path,
    this.release_date,
    this.title,
    this.video,
    this.vote_average,
    this.vote_count,
    movie_type = movieType
)
