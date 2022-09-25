package com.siakang.tukang.presentation.screen.data_completion.bank

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@ExperimentalMaterial3Api
@Composable
fun BankInformationScreen(
    navController: NavHostController,
    viewModel: BankInformationViewModel = hiltViewModel()
) {

    val bankCode by viewModel.bankCode.collectAsState()
    val bankNumber by viewModel.bankNumber.collectAsState()
    val ownerName by viewModel.ownerName.collectAsState()
    val areInpuValid by viewModel.areInputsValid.collectAsState()
    val storeResponse by viewModel.storeResponse.collectAsState(initial = Resource.Empty)

    LaunchedEffect(storeResponse) {
        when(storeResponse) {
            is Resource.Success -> {
                navController.navigate("pending_verification") {
                    popUpTo(0)
                }
            }
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(title = "Daftar Rekening", navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Kode Bank",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(30.dp, 40.dp, 30.dp, 0.dp)
            )
            ValidableTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 18.dp, 30.dp, 0.dp),
                inputWrapper = bankCode,
                placeholder = {
                    Text(
                        fontSize = 14.sp,
                        text = "Kode bank anda"
                    )
                },
                onValueChange = viewModel::onBankCodeEntered,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Text(
                text = "Nama Rekening",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(30.dp, 24.dp, 30.dp, 0.dp)
            )
            ValidableTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 18.dp, 30.dp, 0.dp),
                inputWrapper = ownerName,
                placeholder = {
                    Text(
                        fontSize = 14.sp,
                        text = "Nama rekening anda"
                    )
                },
                onValueChange = viewModel::onOwnerNameEntered,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            Text(
                text = "Nomor Rekening",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(30.dp, 24.dp, 30.dp, 0.dp)
            )
            ValidableTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 18.dp, 30.dp, 0.dp),
                inputWrapper = bankNumber,
                placeholder = {
                    Text(
                        fontSize = 14.sp,
                        text = "Nomor rekening anda"
                    )
                },
                onValueChange = viewModel::onBankNumberEntered,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            LoadingButton(
                text = stringResource(id = R.string.send),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 40.dp, 30.dp, 30.dp)
                    .height(50.dp),
                onClick = viewModel::storeBankInformation,
                enabled = areInpuValid,
                loading = (storeResponse is Resource.Loading)
            )
        }
    }
}