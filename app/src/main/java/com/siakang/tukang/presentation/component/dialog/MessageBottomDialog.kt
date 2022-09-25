package com.siakang.tukang.presentation.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.siakang.tukang.R
import com.siakang.tukang.presentation.theme.Primary

@Composable
fun MessageBottomDialog(
    onDismissAction: ((Boolean) -> Unit),
    onButtonClick: () -> Unit,
    animationRaw: Int = R.raw.something_wrong,
    title: String = "Ups, ada yang salah",
    desc: String = "Cek data anda dan coba lagi."
) {
    BottomSheetDialog(
        onDismissRequest = {
            onDismissAction.invoke(false)
        },
        properties = BottomSheetDialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            dismissWithAnimation = true,
            navigationBarColor = Color.Unspecified
        ),
        content = {
            MessageContent(title, desc, animationRaw, onButtonClick)
        }
    )
}

@Composable
fun MessageContent(
    title: String,
    desc: String,
    animationRaw: Int,
    onButtonClick: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRaw))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(30.dp, 30.dp, 30.dp, 0.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 30.dp, 30.dp, 0.dp),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Primary
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 16.dp, 30.dp, 0.dp),
            text = desc,
            style = MaterialTheme.typography.bodySmall
        )
        Button(
            modifier = Modifier
                .padding(30.dp, 30.dp, 30.dp, 30.dp)
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            ),
            shape = RoundedCornerShape(15.dp),
            onClick = onButtonClick
        ) {
            Text(
                text = "Okey",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
}