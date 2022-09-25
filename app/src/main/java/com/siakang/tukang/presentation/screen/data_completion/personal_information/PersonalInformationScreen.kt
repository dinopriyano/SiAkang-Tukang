package com.siakang.tukang.presentation.screen.data_completion.personal_information

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.siakang.tukang.R
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.domain.usecase.InputWrapper
import com.siakang.tukang.presentation.component.loading_button.LoadingButton
import com.siakang.tukang.presentation.component.text_field.ValidableTextField
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar
import com.siakang.tukang.presentation.theme.Icon
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalMaterial3Api
@Composable
fun PersonalInformationScreen(
    navController: NavHostController,
    viewModel: PersonalInformationViewModel = hiltViewModel()
) {

    val name by viewModel.name.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val emergencyPhone by viewModel.emergencyPhone.collectAsState()
    val areInpuValid by viewModel.areInputsValid.collectAsState()
    val storeResponse by viewModel.storeResponse.collectAsState(initial = Resource.Empty)

    LaunchedEffect(storeResponse) {
        when(storeResponse) {
            is Resource.Success -> {
                navController.navigate("file_upload")
            }
            is Resource.Failure -> {

            }
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(title = "Informasi Pribadi", navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Nama Lengkap",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(30.dp, 40.dp, 30.dp, 0.dp)
            )
            ValidableTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 18.dp, 30.dp, 0.dp),
                inputWrapper = name,
                placeholder = {
                    Text(
                        fontSize = 14.sp,
                        text = "Nama lengkap anda"
                    )
                },
                onValueChange = viewModel::onNameEntered,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            Text(
                text = "No. HP Aktif",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(30.dp, 24.dp, 30.dp, 0.dp)
            )
            ValidableTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 18.dp, 30.dp, 0.dp),
                inputWrapper = phone,
                placeholder = {
                    Text(
                        fontSize = 14.sp,
                        text = "Nomor handphone anda yang aktif"
                    )
                },
                onValueChange = viewModel::onPhoneEntered,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            Text(
                text = "No. HP Darurat",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(30.dp, 24.dp, 30.dp, 0.dp)
            )
            ValidableTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 18.dp, 30.dp, 0.dp),
                inputWrapper = emergencyPhone,
                placeholder = {
                    Text(
                        fontSize = 14.sp,
                        text = "Nomor handphone darurat"
                    )
                },
                onValueChange = viewModel::onEmergencyPhoneEntered,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            LoadingButton(
                text = stringResource(id = R.string.next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 40.dp, 30.dp, 30.dp)
                    .height(50.dp),
                onClick = viewModel::storePersonalInformation,
                enabled = areInpuValid,
                loading = storeResponse is Resource.Loading
            )
        }
    }
}