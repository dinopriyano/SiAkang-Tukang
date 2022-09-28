package com.siakang.tukang.presentation.component.toolbar

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.siakang.tukang.R
import com.siakang.tukang.core.domain.model.User
import com.siakang.tukang.presentation.theme.Dark
import com.siakang.tukang.presentation.theme.Hint
import com.siakang.tukang.presentation.theme.Primary

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun ChatToolbar(
    navController: NavHostController,
    user: User
) {

    val context = LocalContext.current
    val phonePermissionState = rememberPermissionState(
        Manifest.permission.CALL_PHONE
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(bottom = 30.dp, top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back"
            )
        }
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.photo_profile.orEmpty().toUri())
                    .size(50) // Set the target size to load the image at.
                    .build(), filterQuality = FilterQuality.Low
            ),
            contentDescription = "User Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 10.dp)
                .width(48.dp)
                .aspectRatio(1f)
                .align(
                    Alignment.CenterVertically
                )
                .clip(shape = CircleShape)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 15.dp)
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 14.sp,
                    color = Dark
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = user.phone,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    color = Hint
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        IconButton(
            onClick = {
                if(phonePermissionState.status == PermissionStatus.Granted) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_CALL,
                            Uri.parse("tel:${user.phone}")
                        )
                    )
                }
                else {
                    phonePermissionState.launchPermissionRequest()
                }
            }, modifier = Modifier.padding(end = 20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_phone),
                contentDescription = null,
                tint = Primary
            )
        }
    }
}