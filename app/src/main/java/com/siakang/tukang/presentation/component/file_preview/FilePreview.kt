package com.siakang.tukang.presentation.component.file_preview

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.siakang.tukang.presentation.theme.Dark

@Composable
fun FilePreview(
    modifier: Modifier,
    imageColor: Color,
    imageUri: Uri,
    imageName: String,
    onClick: (Uri) -> Unit
) {
    Row(
        modifier = modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() } // This is mandatory
        ) {
            onClick.invoke(imageUri)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .width(32.dp)
                .aspectRatio(1f),
            shape = RoundedCornerShape(7.dp),
            colors = CardDefaults.cardColors(
                containerColor = imageColor
            )
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = imageUri,
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            text = imageName, style = MaterialTheme.typography.labelSmall.copy(
                color = Dark
            )
        )
    }
}