package com.rpfcoding.myposwithjetpackcompose.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.rpfcoding.myposwithjetpackcompose.util.Constants
import com.rpfcoding.myposwithjetpackcompose.util.Constants.countries

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MySpinner(
    onItemSelected: (String) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedOptionText by remember {
        mutableStateOf(countries[0])
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(text = "Countries") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            countries.forEach { country ->
                DropdownMenuItem(onClick = {
                    selectedOptionText = country
                    expanded = false
                    onItemSelected(country)
                }) {
                    Text(text = country)
                }
            }
        }
    }
}