package com.siakang.tukang.presentation.component.file_upload

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.siakang.tukang.R
import com.siakang.tukang.presentation.theme.Border
import com.siakang.tukang.presentation.theme.Primary

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun FileUpload(
    modifier: Modifier,
    uri: Uri? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Border
        ),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            if (uri == null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.padding(20.dp, 28.dp, 20.dp, 0.dp),
                        painter = painterResource(id = R.drawable.ic_cloud_upload),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 28.dp),
                        text = "Masukan data anda disini",
                        style = MaterialTheme.typography.bodySmall,
                        color = Primary
                    )
                }
            } else {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    model = uri,
                    contentDescription = null
                )
            }
        }
    }
}