package com.siakang.tukang.presentation.component.title_description

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.siakang.tukang.presentation.theme.Header
import com.siakang.tukang.presentation.theme.Placeholder

@Composable
fun TitleDescription(
    title: String,
    desc: String,
    modifier: Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        fontSize = 22.sp,
        color = Header
    ),
    descStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(
        fontSize = 14.sp,
        color = Placeholder
    ),
    spacerHeight: Dp = 10.dp
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            lineHeight = 20.sp,
            style = titleStyle
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(spacerHeight)
        )
        Text(
            text = desc,
            lineHeight = 20.sp,
            style = descStyle
        )
    }
}