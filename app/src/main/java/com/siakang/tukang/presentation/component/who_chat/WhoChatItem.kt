package com.siakang.tukang.presentation.component.who_chat

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.siakang.tukang.core.domain.model.WhoChat
import com.siakang.tukang.core.utils.ext.toDateString
import com.siakang.tukang.presentation.theme.Border
import com.siakang.tukang.presentation.theme.Dark
import com.siakang.tukang.presentation.theme.Hint

@ExperimentalMaterial3Api
@Composable
fun WhoChatItem(
    modifier: Modifier,
    whoChat: WhoChat,
    userId: String,
    onClick: (String) -> Unit
) {
    var receiverImageUri = Uri.EMPTY
    var receiverName = ""
    var receiverId = ""

    if (whoChat.usersId[0] == userId) {
        receiverImageUri = whoChat.usersProfile[1].toUri()
        receiverName = whoChat.usersName[1]
        receiverId = whoChat.usersId[1]
    } else {
        receiverImageUri = whoChat.usersProfile[0].toUri()
        receiverName = whoChat.usersName[0]
        receiverId = whoChat.usersId[0]
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Border),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = { onClick.invoke(receiverId) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(receiverImageUri)
                        .size(50) // Set the target size to load the image at.
                        .build(), filterQuality = FilterQuality.Low
                ),
                contentDescription = "User Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(48.dp)
                    .aspectRatio(1f)
                    .align(
                        Alignment.CenterVertically
                    )
                    .clip(shape = CircleShape)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = receiverName,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            color = Dark
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = whoChat.chatDate.toDateString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 12.sp,
                            color = Hint
                        )
                    )
                }
                Text(
                    text = whoChat.lastChat,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        color = Hint
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}