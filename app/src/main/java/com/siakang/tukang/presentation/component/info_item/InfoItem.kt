package com.siakang.tukang.presentation.component.info_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.siakang.tukang.R
import com.siakang.tukang.presentation.theme.Dark

@Composable
fun InfoItem(
    modifier: Modifier,
    circleColor: Color,
    icon: Painter,
    name: String,
    onClick: () -> Unit
) {
    Row(modifier = modifier.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() } // This is mandatory
    ) {
        onClick.invoke()
    }, verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(40.dp), shape = CircleShape, colors = CardDefaults.cardColors(
                containerColor = circleColor
            )
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                painter = icon,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp),
            text = name,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = Dark
            )
        )
        IconButton(
            onClick = {}
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_detail_arrow),
                contentDescription = null,
                tint = Dark
            )
        }
    }
}