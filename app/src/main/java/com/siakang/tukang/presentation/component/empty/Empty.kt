package com.siakang.tukang.presentation.component.empty

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.siakang.tukang.R
import com.siakang.tukang.presentation.theme.Placeholder
import com.siakang.tukang.presentation.theme.Primary

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@Composable
fun EmptyState(
    imageResource: Int = R.drawable.ic_verifikasi,
    isAnimation: Boolean,
    title: String,
    description: String,
    textButton: String,
    onButtonClick: (() -> Unit)
) {

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        if(isAnimation){
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(imageResource))
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 64.dp, 30.dp, 0.dp)
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth
            )
        }
        else {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 64.dp, 30.dp, 0.dp)
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = imageResource),
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 24.dp, 30.dp, 0.dp),
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 18.dp, 30.dp, 0.dp),
            text = description,
            color = Placeholder,
            style = MaterialTheme.typography.bodySmall
        )
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 24.dp, 30.dp, 0.dp)
                .height(50.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, Primary),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Primary
            ),
            onClick = onButtonClick
        ) {
            Text(
                text = textButton,
                color = Primary,
                modifier = Modifier.layoutId("buttonText"),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }

}