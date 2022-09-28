package com.siakang.tukang.presentation.screen.dashboard.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.siakang.tukang.R
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.presentation.component.info_item.InfoItem
import com.siakang.tukang.presentation.component.loading_button.LoadingButton
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar
import com.siakang.tukang.presentation.component.who_chat.WhoChatItem
import com.siakang.tukang.presentation.theme.BlueLogout
import com.siakang.tukang.presentation.theme.Dark
import com.siakang.tukang.presentation.theme.Primary
import com.siakang.tukang.presentation.theme.Secondary

@ExperimentalMaterial3Api
@Composable
fun ProfileScreen(
    parentNavController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val user = viewModel.user

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(
                title = "Profil",
                navController = parentNavController,
                hideBackArrow = true
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    12.dp
                ),
                shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(user.photoProfile)
                            .size(80) // Set the target size to load the image at.
                            .build(), filterQuality = FilterQuality.High
                    ),
                    contentDescription = "User Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(20.dp, 30.dp, 20.dp, 0.dp)
                        .width(113.dp)
                        .aspectRatio(1f)
                        .clip(shape = CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(top = 24.dp),
                    text = user.name,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 22.sp,
                        color = Dark,
                        textAlign = TextAlign.Center
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(top = 12.dp),
                    text = user.phone,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 16.sp,
                        color = Secondary,
                        textAlign = TextAlign.Center
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                LoadingButton(
                    text = "Ubah Profil",
                    colors = Primary,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .padding(top = 24.dp)
                        .width(200.dp)
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        parentNavController.navigate("edit_profile")
                    },
                    enabled = true,
                    loading = false
                )
                Spacer(modifier = Modifier.height(47.dp))
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = 40.dp),
                text = "Info lainnya",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp,
                    color = Dark
                )
            )
            InfoItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = 26.dp),
                circleColor = BlueLogout,
                icon = painterResource(id = R.drawable.ic_leave),
                name = "Logout"
            ) {
                viewModel.clearLoginSession()
                parentNavController.navigate("login")
            }
        }
    }
}