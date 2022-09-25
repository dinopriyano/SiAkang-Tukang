package com.siakang.tukang.presentation.screen.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.siakang.tukang.R
import com.siakang.tukang.presentation.component.dialog.MessageBottomDialog
import com.siakang.tukang.presentation.component.loading_button.LoadingButton
import com.siakang.tukang.presentation.component.text_field.ValidableTextField
import com.siakang.tukang.presentation.component.title_description.TitleDescription
import com.siakang.tukang.presentation.theme.*
import com.siakang.tukang.utils.ext.toast
import com.siakang.tukang.core.data.model.Resource

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val areInputsValid by viewModel.areInputsValid.collectAsState()
    val registerResponse by viewModel.registerResponse.collectAsState(initial = Resource.Empty)
    val saveUserResponse by viewModel.saveResponse.collectAsState(initial = Resource.Empty)

    val context = LocalContext.current

    var showDialog by remember {
        mutableStateOf(Pair(false, ""))
    }

    if (showDialog.first) {
        MessageBottomDialog(
            onDismissAction = {
                showDialog = showDialog.copy(first = false)
            },
            onButtonClick = {

            },
            desc = showDialog.second
        )
    }

    LaunchedEffect(registerResponse) {
        when (registerResponse) {
            is Resource.Success -> {
                viewModel.saveUser((registerResponse as Resource.Success).value?.uid.orEmpty())
            }
            is Resource.Failure -> {
                showDialog = Pair(true, (registerResponse as Resource.Failure).message)
            }
            else -> Unit
        }
    }

    LaunchedEffect(saveUserResponse) {
        when(saveUserResponse) {
            is Resource.Success -> {
                navController.navigate("register_success")
            }
            is Resource.Failure -> {
                showDialog = Pair(true, (saveUserResponse as Resource.Failure).message)
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        TitleDescription(
            title = stringResource(id = R.string.register_title),
            desc = stringResource(id = R.string.register_desc),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 80.dp, 30.dp, 0.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            text = stringResource(id = R.string.email),
            style = MaterialTheme.typography.bodyMedium,
            color = Label
        )
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 5.dp, 30.dp, 0.dp),
            inputWrapper = email,
            placeholder = {
                Text(
                    fontSize = 14.sp,
                    text = stringResource(id = R.string.email_hint)
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_mail),
                    contentDescription = "Mail Icon",
                    tint = Icon
                )
            },
            onValueChange = viewModel::onEmailEntered,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 25.dp, 30.dp, 0.dp),
            text = stringResource(id = R.string.password),
            style = MaterialTheme.typography.bodyMedium,
            color = Label
        )
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 5.dp, 30.dp, 0.dp),
            inputWrapper = password,
            placeholder = {
                Text(
                    fontSize = 14.sp,
                    text = stringResource(id = R.string.password_hint)
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = image,
                        contentDescription = "Password Toggle",
                        tint = Icon
                    )
                }
            },
            onValueChange = viewModel::onPasswordEntered,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 25.dp, 30.dp, 0.dp),
            text = stringResource(id = R.string.confirm_password),
            style = MaterialTheme.typography.bodyMedium,
            color = Label
        )
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 5.dp, 30.dp, 0.dp),
            inputWrapper = confirmPassword,
            placeholder = {
                Text(
                    fontSize = 14.sp,
                    text = stringResource(id = R.string.confirm_password_hint)
                )
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (confirmPasswordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = image,
                        contentDescription = "Password Toggle",
                        tint = Icon
                    )
                }
            },
            onValueChange = viewModel::onConfirmPasswordEntered,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
        LoadingButton(
            text = stringResource(id = R.string.register),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 36.dp, 30.dp, 0.dp)
                .height(50.dp),
            onClick = viewModel::createAccountWithEmailPassword,
            enabled = areInputsValid,
            loading = registerResponse is Resource.Loading
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(33.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ) {
            Divider(
                Modifier
                    .weight(1f)
                    .height(1.dp), color = Border
            )
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(5.dp, 0.dp, 5.dp, 0.dp),
                text = stringResource(id = R.string.or),
                style = MaterialTheme.typography.labelSmall,
                color = Label
            )
            Divider(
                Modifier
                    .weight(1f)
                    .height(1.dp), color = Border
            )

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.size(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Stroke),
                onClick = {

                }
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Icon",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                modifier = Modifier.size(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Stroke),
                onClick = {

                }
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_facebook),
                    contentDescription = "Facebook Icon",
                    tint = Color.Unspecified
                )
            }
        }
        Spacer(modifier = Modifier.height(36.dp))
        ClickableText(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 30.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Dark)) {
                    append(stringResource(id = R.string.have_an_account))
                }
                withStyle(style = SpanStyle(color = Primary)) {
                    append(" ${stringResource(id = R.string.login)}")
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            onClick = {
                navController.popBackStack()
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
//    RegisterScreen()
}

