package com.tp.testrap.ui.movieDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tp.testrap.R

@Composable
fun DescriptionContent(movieDetailFragmentArgs: MovieDetailFragmentArgs) {
    Surface {
        Column(modifier = Modifier.background(Color.Black)) {
            ItemsDetail(movieDetailFragmentArgs)
            DescriptionMovie(movieDetailFragmentArgs.overview)
        }
    }
}

@Composable
fun ItemsDetail(movieDetail: MovieDetailFragmentArgs) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Year(date = movieDetail.releaseDate)
        Language(l = movieDetail.language)
        Score(number = String.format("%.2f", movieDetail.voteAverage))
    }
}

@Composable
fun Year(date: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = date.substring(0, 4),
            modifier = Modifier.padding(10.dp),
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}

@Composable
fun Language(l: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = l,
            modifier = Modifier.padding(10.dp),
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}

@Composable
fun Score(number: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),

        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {

        Row ( modifier = Modifier.background(Color(0xFFFFEB3B)).padding(10.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "star rainting",
                modifier = Modifier.size(16.dp).padding(end = 2.dp)
            )
            Text(
                text = number,
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 2.dp)
            )

        }
    }
}

//@Preview
@Composable
fun DescriptionMovie(movieDetailFragmentArgs: String) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Descripción",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = movieDetailFragmentArgs,
            color = Color.White
        )
    }
}