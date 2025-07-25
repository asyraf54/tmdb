package com.example.tmdb.core.widgets


import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import com.example.tmdb.domain.entity.Movie

@Composable
fun MovieCard(
    movie: Movie,
    onClick: (() -> Unit)
) {
    Column {
        AsyncImageBase(
            imageUrl = "https://image.tmdb.org/t/p/original${movie.posterPath}",
            contentDescription = movie.title,
            modifier = Modifier
                .width(128.dp)
                .height(188.dp)
                .clip(RoundedCornerShape(12.dp))
                .shadow(4.dp, RoundedCornerShape(12.dp))
                .clickable(onClick = onClick),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(128.dp)
        )
    }
}
