package com.siakang.tukang.presentation.screen.registration_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.siakang.tukang.R
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.presentation.component.loading_button.LoadingButton
import com.siakang.tukang.presentation.component.title_description.TitleDescription
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar
import com.siakang.tukang.presentation.theme.Border
import com.siakang.tukang.presentation.theme.Dark
import com.siakang.tukang.presentation.theme.Header
import com.siakang.tukang.presentation.theme.Placeholder

@ExperimentalMaterial3Api
@Composable
fun RegistrationDetailScreen(
    navController: NavHostController
) {
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
                    desc = "-",
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
                    desc = "-",
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
                    desc = "-",
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
                    desc = "-",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 18.dp, 18.dp, 0.dp),
                    titleStyle = MaterialTheme.typography.labelSmall.copy(
                        color = Placeholder
                    ),
                    descStyle = MaterialTheme.typography.labelMedium,
                    spacerHeight = 8.dp
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
            LoadingButton(
                text = stringResource(id = R.string.home),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 36.dp, 30.dp, 0.dp)
                    .height(50.dp),
                onClick = {

                },
                enabled = true,
                loading = false
            )
        }
    }
}