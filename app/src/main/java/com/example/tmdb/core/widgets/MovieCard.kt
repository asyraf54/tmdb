package com.example.tmdb.core.widgets


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import com.example.tmdb.domain.entity.Movie

@Composable
fun MovieCard(movie: Movie, onClick: (() -> Unit)? = null) {
    Box(modifier = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }) {
        Column {
            AsyncImageBase(
                imageUrl = "https://image.tmdb.org/t/p/original${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .width(128.dp)
                    .height(188.dp),
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
}
