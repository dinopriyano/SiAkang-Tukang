package com.siakang.tukang.presentation.screen.login

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
import com.google.accompanist.insets.ProvideWindowInsets
import com.siakang.tukang.R
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.presentation.component.dialog.MessageBottomDialog
import com.siakang.tukang.presentation.component.loading_button.LoadingButton
import com.siakang.tukang.presentation.component.text_field.ValidableTextField
import com.siakang.tukang.presentation.component.title_description.TitleDescription
import com.siakang.tukang.presentation.theme.*
import com.siakang.tukang.utils.ext.findActivity

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    setWhiteStatusBar()

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val areInputsValid by viewModel.areInputsValid.collectAsState()
    val context = LocalContext.current

    val loginResponse by viewModel.loginResponse.collectAsState(initial = Resource.Empty)
    val userResponse by viewModel.userResponse.collectAsState(initial = Resource.Empty)

    var passwordVisible by remember { mutableStateOf(false) }

    var showDialog by remember {
        mutableStateOf(Pair(false, ""))
    }

    if (showDialog.first) {
        MessageBottomDialog(
            onDismissAction = {
                showDialog = showDialog.copy(first = false)
            },
            onButtonClick = {
                context.findActivity()?.onBackPressed()
            },
            desc = showDialog.second
        )
    }

    LaunchedEffect(loginResponse) {
        when (loginResponse) {
            is Resource.Success -> {
                val uid = (loginResponse as Resource.Success).value?.uid.orEmpty()
                viewModel.storeUid(uid)
                viewModel.getUser(uid)
            }
            is Resource.Failure -> {
                showDialog = Pair(true, (loginResponse as Resource.Failure).message)
            }
            else -> Unit
        }
    }

    LaunchedEffect(userResponse) {
        when(userResponse) {
            is Resource.Success -> {
                (userResponse as Resource.Success).value?.let { user ->
                    if(user.areDataComplete) {
                        navController.navigate("dashboard")
                    }
                    else {
                        navController.navigate("personal_information")
                    }
                }
            }
            is Resource.Failure -> {
                showDialog = Pair(true, (loginResponse as Resource.Failure).message)
            }
            else -> Unit
        }
    }

    ProvideWindowInsets {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            TitleDescription(
                title = stringResource(id = R.string.login_title),
                desc = stringResource(id = R.string.login_desc),
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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        fontSize = 14.sp,
                        text = stringResource(id = R.string.password_hint)
                    )
                },
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
                    imeAction = ImeAction.Done
                )
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
            )
            ClickableText(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.End)
                    .padding(horizontal = 30.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Primary)) {
                        append(stringResource(id = R.string.forgot_password))
                    }
                },
                style = MaterialTheme.typography.bodySmall,
                onClick = {

                }
            )
            LoadingButton(
                text = stringResource(id = R.string.login),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 36.dp, 30.dp, 0.dp)
                    .height(50.dp),
                onClick = viewModel::loginWithEmailPassword,
                enabled = areInputsValid,
                loading = loginResponse is Resource.Loading
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
                        append(stringResource(id = R.string.not_have_account))
                    }
                    withStyle(style = SpanStyle(color = Primary)) {
                        append(" ${stringResource(id = R.string.register)}")
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                onClick = {
                    navController.navigate("register")
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
//    LoginScreen()
}