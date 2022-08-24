package com.rpfcoding.myposwithjetpackcompose.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun MyOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    placeholder: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    maxLength: Int = 50,
    keyboardOpts: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    modifier: Modifier = Modifier
) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.length <= maxLength) onValueChange(it)
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
        visualTransformation = if (visualTransformation is PasswordVisualTransformation) {
            if (isVisible) VisualTransformation.None else visualTransformation
        } else visualTransformation,
        modifier = modifier,
        maxLines = maxLines,
        keyboardOptions = keyboardOpts,
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = "Icon")
        },
        trailingIcon = {
            if (visualTransformation is PasswordVisualTransformation) {
                Icon(
                    imageVector = if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        isVisible = !isVisible
                    }
                )
            }
        }
    )
}