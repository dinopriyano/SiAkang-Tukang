package com.siakang.tukang.presentation.component.chat_item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.siakang.tukang.presentation.theme.Primary
import com.siakang.tukang.R
import com.siakang.tukang.presentation.theme.Dark
import com.siakang.tukang.presentation.theme.ReceiverChat

@Preview
@Composable
fun SenderEndChatItem(
    message: String = "Hallo World!",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        Card(
            modifier = Modifier
                .padding(start = 80.dp),
            shape = RoundedCornerShape(
                topEnd = 6.dp,
                topStart = 6.dp,
                bottomStart = 6.dp,
                bottomEnd = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Primary
            )
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = message,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Color.White
                )
            )
        }
    }
}

@Preview
@Composable
fun ReceiverEndChatItem(
    message: String = "Hallo World!",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .padding(end = 80.dp),
            shape = RoundedCornerShape(
                topEnd = 6.dp,
                topStart = 6.dp,
                bottomStart = 0.dp,
                bottomEnd = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = ReceiverChat
            )
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = message,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Dark
                )
            )
        }
    }
}

@Preview
@Composable
fun ReceiverChatItem(
    message: String = "Hallo World!",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = ReceiverChat
            )
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = message,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Dark
                )
            )
        }
    }
}

@Preview
@Composable
fun SenderChatItem(
    message: String = "Hallo World!",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        Card(
            modifier = Modifier
                .padding(start = 80.dp),
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Primary
            )
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = message,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Color.White
                )
            )
        }
    }
}