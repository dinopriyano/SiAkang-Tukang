package com.siakang.tukang.presentation.screen.registration_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.siakang.tukang.R
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.presentation.component.file_preview.FilePreview
import com.siakang.tukang.presentation.component.loading_button.LoadingButton
import com.siakang.tukang.presentation.component.title_description.TitleDescription
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar
import com.siakang.tukang.presentation.theme.*

@ExperimentalMaterial3Api
@Composable
fun RegistrationDetailScreen(
    navController: NavHostController,
    viewModel: RegistrationDetailViewModel = hiltViewModel()
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

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(title = "Detail Pendaftaran", navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 40.dp, 30.dp, 0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Border),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp),
                    text = "Informasi Pribadi",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Dark
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                        .padding(top = 18.dp), thickness = 1.dp, color = Border
                )
                TitleDescription(
                    title = "Nama Lengkap",
                    desc = user.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 18.dp, 18.dp, 0.dp),
                    titleStyle = MaterialTheme.typography.labelSmall.copy(
                        color = Placeholder
                    ),
                    descStyle = MaterialTheme.typography.labelMedium,
                    spacerHeight = 8.dp
                )
                TitleDescription(
                    title = "No. Hp Aktif",
                    desc = user.phone,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 18.dp, 18.dp, 0.dp),
                    titleStyle = MaterialTheme.typography.labelSmall.copy(
                        color = Placeholder
                    ),
                    descStyle = MaterialTheme.typography.labelMedium,
                    spacerHeight = 8.dp
                )
                TitleDescription(
                    title = "No. Hp Darurat",
                    desc = user.emergencyPhone,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 18.dp, 18.dp, 0.dp),
                    titleStyle = MaterialTheme.typography.labelSmall.copy(
                        color = Placeholder
                    ),
                    descStyle = MaterialTheme.typography.labelMedium,
                    spacerHeight = 8.dp
                )
                TitleDescription(
                    title = "Email",
                    desc = user.email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 18.dp, 18.dp, 0.dp),
                    titleStyle = MaterialTheme.typography.labelSmall.copy(
                        color = Placeholder
                    ),
                    descStyle = MaterialTheme.typography.labelMedium,
                    spacerHeight = 8.dp
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(18.dp, 18.dp, 18.dp, 0.dp),
                    text = "File",
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Placeholder
                    )
                )
                FilePreview(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 8.dp, 18.dp, 0.dp),
                    imageColor = Secondary,
                    imageUri = user.photoProfile.orEmpty().toUri(),
                    imageName = "Photo Profile"
                ) { uri ->
                    navController.navigate("image_detail?imageUrl=$uri")
                }
                FilePreview(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 8.dp, 18.dp, 0.dp),
                    imageColor = Primary,
                    imageUri = user.idCard.orEmpty().toUri(),
                    imageName = "Kartu Tanda Penduduk",
                ){ uri ->
                    navController.navigate("image_detail?imageUrl=$uri")
                }
                FilePreview(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 8.dp, 18.dp, 0.dp),
                    imageColor = Header,
                    imageUri = user.skck.orEmpty().toUri(),
                    imageName = "Surat Keterangan Catatan Kepolisian"
                ){ uri ->
                    navController.navigate("image_detail?imageUrl=$uri")
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(18.dp, 18.dp, 18.dp, 0.dp),
                    text = "Rekening",
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Placeholder
                    )
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp, 8.dp, 18.dp, 0.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight(),
                        text = user.ownerName,
                        lineHeight = 20.sp,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "${user.bankNumber}-${user.codeBank}",
                        lineHeight = 20.sp,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Placeholder
                        )
                    )
                }
                TitleDescription(
                    title = "Status Pendaftaran",
                    desc = if(user.status) "Diterima" else "Pending",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 18.dp, 18.dp, 0.dp),
                    titleStyle = MaterialTheme.typography.labelSmall.copy(
                        color = Placeholder
                    ),
                    descStyle = MaterialTheme.typography.labelMedium.copy(
                        color = if(user.status) Color.Green else Secondary
                    ),
                    spacerHeight = 8.dp
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
            LoadingButton(
                text = stringResource(id = R.string.home),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 36.dp, 30.dp, 30.dp)
                    .height(50.dp),
                onClick = {
                    navController.navigate("dashboard")
                },
                enabled = user.status,
                loading = false
            )
        }
    }
}