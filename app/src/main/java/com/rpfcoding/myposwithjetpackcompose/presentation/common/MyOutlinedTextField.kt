package com.rpfcoding.myposwithjetpackcompose.presentation.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun MyOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    maxLength: Int = 50,
    keyboardOpts: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if(it.length <= maxLength) onValueChange(it)
        },
        placeholder = {
            if (placeholder != null) {
                Text(text = placeholder)
            }
        },
        label = {
            if (placeholder != null) {
                Text(text = placeholder)
            }
        },
        visualTransformation = visualTransformation,
        modifier = modifier,
        maxLines = maxLines,
        keyboardOptions = keyboardOpts,

        )
}