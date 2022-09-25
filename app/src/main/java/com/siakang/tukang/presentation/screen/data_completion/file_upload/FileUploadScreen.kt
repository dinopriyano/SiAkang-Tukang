package com.siakang.tukang.presentation.screen.data_completion.file_upload

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.siakang.tukang.R
import com.siakang.tukang.presentation.component.file_upload.FileUpload
import com.siakang.tukang.presentation.component.loading_button.LoadingButton
import com.siakang.tukang.presentation.component.title_description.TitleDescription
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun FileUploadScreen(
    navController: NavHostController,
    viewModel: FileUploadViewModel = hiltViewModel()
) {

    val areInputValid by viewModel.areInputsValid.collectAsState()
    val photoProfile by viewModel.photoProfile.collectAsState()
    val idCard by viewModel.idCard.collectAsState()
    val skck by viewModel.skck.collectAsState()

    val readExternalStoragePermissionState = rememberPermissionState(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val photoProfileLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.setPhotoProfile(it)
        }
    }
    val idCardLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.setIdCard(it)
        }
    }
    val skckLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.setSkck(it)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(
                title = stringResource(R.string.file_upload),
                navController = navController
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            TitleDescription(
                title = stringResource(id = R.string.file_upload),
                desc = "Mohon untuk mengunggah berkas-berkas berikut.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 40.dp, 30.dp, 0.dp),
                titleStyle = MaterialTheme.typography.labelMedium,
                descStyle = MaterialTheme.typography.bodySmall,
                spacerHeight = 8.dp
            )
            Text(
                text = "Foto Profil",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(30.dp, 24.dp, 30.dp, 0.dp)
            )
            FileUpload(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 18.dp, 30.dp, 0.dp),
                uri = photoProfile,
                onClick = {
                    if(readExternalStoragePermissionState.status != PermissionStatus.Granted) {
                        readExternalStoragePermissionState.launchPermissionRequest()
                    }
                    else {
                        photoProfileLauncher.launch("image/*")
                    }
                }
            )
            Text(
                text = "Kartu Tanda Penduduk",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(30.dp, 24.dp, 30.dp, 0.dp)
            )
            FileUpload(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 18.dp, 30.dp, 0.dp),
                uri = idCard,
                onClick = {
                    if(readExternalStoragePermissionState.status != PermissionStatus.Granted) {
                        readExternalStoragePermissionState.launchPermissionRequest()
                    }
                    else {
                        idCardLauncher.launch("image/*")
                    }
                }
            )
            Text(
                text = "SKCK",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(30.dp, 24.dp, 30.dp, 0.dp)
            )
            FileUpload(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 18.dp, 30.dp, 0.dp),
                uri = skck,
                onClick = {
                    if(readExternalStoragePermissionState.status != PermissionStatus.Granted) {
                        readExternalStoragePermissionState.launchPermissionRequest()
                    }
                    else {
                        skckLauncher.launch("image/*")
                    }
                }
            )
            LoadingButton(
                text = stringResource(id = R.string.next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 40.dp, 30.dp, 30.dp)
                    .height(50.dp),
                onClick = {

                },
                enabled = areInputValid,
                loading = false
            )
        }
    }
}