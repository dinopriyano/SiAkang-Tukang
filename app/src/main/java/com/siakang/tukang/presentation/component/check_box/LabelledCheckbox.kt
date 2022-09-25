package com.siakang.tukang.presentation.component.check_box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.siakang.tukang.presentation.theme.Placeholder
import com.siakang.tukang.presentation.theme.Primary

@Composable
fun LabelledCheckbox(
    modifier: Modifier,
    label: String,
    isChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)
) {
    Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            modifier = modifier,
            checked = isChecked,
            onCheckedChange = { onCheckedChange.invoke(!isChecked) },
            enabled = true,
            colors = CheckboxDefaults.colors(
                checkedColor = Primary,
                uncheckedColor = Primary,
                checkmarkColor = Color.White
            )
        )
        Text(
            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp),
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Placeholder
            )
        )
    }
}