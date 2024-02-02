package com.tp.testrap.ui.movieDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DescriptionContent(movieDetailFragmentArgs: MovieDetailFragmentArgs) {
    Surface {
        DescriptionMovie(movieDetailFragmentArgs)
    }
}

//@Preview
@Composable
fun DescriptionMovie(movieDetailFragmentArgs: MovieDetailFragmentArgs) {
    Column (modifier = Modifier
        .background(Color.Black)
        .fillMaxWidth()
        .padding(horizontal = 16.dp)){
        Text(
            text = "Descripción",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = movieDetailFragmentArgs.overview,
            color = Color.White
        )
    }
}