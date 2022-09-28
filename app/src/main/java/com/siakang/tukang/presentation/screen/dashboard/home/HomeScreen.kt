package com.siakang.tukang.presentation.screen.dashboard.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.siakang.tukang.R
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.presentation.component.tab.Tabs
import com.siakang.tukang.presentation.component.tab.TabsContent
import com.siakang.tukang.presentation.component.title_description.TitleDescription
import com.siakang.tukang.presentation.theme.Border
import com.siakang.tukang.presentation.theme.Header
import com.siakang.tukang.presentation.theme.Hint
import com.siakang.tukang.presentation.theme.Primary
import com.siakang.tukang.utils.ext.getFirstName

@ExperimentalPagerApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    parentNavController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    viewModel.getUser()
    val pagerState = rememberPagerState()
    var user by remember {
        mutableStateOf(Tukang())
    }
    val userResponse by viewModel.userResponse.collectAsState(initial = Resource.Empty)

    LaunchedEffect(userResponse) {
        when (userResponse) {
            is Resource.Success -> {
                val userResult = (userResponse as Resource.Success).value ?: Tukang()
                viewModel.storeSkills(userResult.skills)
                user = userResult
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 24.dp, 30.dp, 0.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, Border),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = com.siakang.tukang.presentation.theme.Icon
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp),
                    text = "Cari disini",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Hint,
                        fontSize = 14.sp
                    )
                )
                Divider(
                    modifier = Modifier
                        .height(25.dp)
                        .width(1.dp),
                    color = Border
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = null,
                    tint = com.siakang.tukang.presentation.theme.Icon
                )
            }
        }
        Tabs(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(top = 15.dp), tabs = TabItem.Items.items, pagerState = pagerState
        )
        TabsContent(
            tabs = TabItem.Items.items,
            pagerState = pagerState,
            parentNavController = parentNavController
        )
    }
}