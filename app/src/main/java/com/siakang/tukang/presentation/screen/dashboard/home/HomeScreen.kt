package com.siakang.tukang.presentation.screen.dashboard.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.presentation.component.title_description.TitleDescription
import com.siakang.tukang.presentation.theme.Header
import com.siakang.tukang.presentation.theme.Primary
import com.siakang.tukang.utils.ext.getFirstName

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    viewModel.getUser()
    var user by remember {
        mutableStateOf(Tukang())
    }
    val userResponse by viewModel.userResponse.collectAsState(initial = Resource.Empty)

    LaunchedEffect(userResponse) {
        when (userResponse) {
            is Resource.Success -> {
                user = (userResponse as Resource.Success).value ?: Tukang()
            }
            else -> Unit
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(30.dp, 44.dp, 30.dp, 0.dp)
            ) {
                TitleDescription(
                    title = "Hi, ${user.name.getFirstName()}",
                    desc = "Semangat terus ya..",
                    modifier = Modifier
                        .weight(1f),
                    titleStyle = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        color = Header
                    ),
                    descStyle = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 20.sp,
                        color = Header
                    ),
                    spacerHeight = 5.dp
                )
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(user.photoProfile)
                            .size(80) // Set the target size to load the image at.
                            .build(), filterQuality = FilterQuality.Low
                    ),
                    contentDescription = "User Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(20.dp, 0.dp, 0.dp, 0.dp)
                        .width(48.dp)
                        .aspectRatio(1f)
                        .align(
                            Alignment.CenterVertically
                        )
                        .clip(shape = CircleShape)
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 24.dp, 30.dp, 0.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Primary),
            ) {

            }
        }
    }
}