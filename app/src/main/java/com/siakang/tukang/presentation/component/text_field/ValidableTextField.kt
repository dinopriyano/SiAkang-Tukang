package com.siakang.tukang.presentation.component.text_field

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.siakang.tukang.domain.usecase.InputWrapper
import com.siakang.tukang.presentation.theme.Border
import com.siakang.tukang.presentation.theme.Label
import com.siakang.tukang.presentation.theme.Placeholder
import com.siakang.tukang.presentation.theme.Primary

@Composable
fun ValidableTextField(
    modifier: Modifier,
    inputWrapper: InputWrapper,
    keyboardOptions: KeyboardOptions = remember {
        KeyboardOptions.Default
    },
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = remember {
        VisualTransformation.None
    },
    onValueChange: (value: String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = inputWrapper.value,
            shape = RoundedCornerShape(15.dp),
            isError = inputWrapper.error != null,
            singleLine = true,
            visualTransformation = visualTransformation,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Border,
                placeholderColor = Placeholder,
                textColor = Label
            ),
            keyboardOptions = keyboardOptions,
            textStyle = MaterialTheme.typography.bodySmall,
            placeholder = placeholder,
            trailingIcon = trailingIcon
        )
        if (inputWrapper.error != null) {
            Text(
                modifier = Modifier.padding(16.dp, 4.dp, 0.dp, 0.dp),
                text = inputWrapper.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}