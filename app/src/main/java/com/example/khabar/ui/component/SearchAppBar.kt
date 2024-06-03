package com.example.khabar.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.example.khabar.R

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClose: () -> Unit,
){
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 16.sp
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search),
                tint = Color.White.copy(alpha = 0.7f)
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search_placeholder),
                color = Color.White.copy(alpha = 0.7f)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    if (value.isNotEmpty())
                        onValueChange("")
                    else
                        onClose()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(value)
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor =  MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor =  MaterialTheme.colorScheme.primaryContainer,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.White
        )
    )
}