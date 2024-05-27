package com.example.academia.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.academia.ui.theme.ORANGE

@Composable
fun OutlinedTextFieldCustom(
    value: String,
    onValueChange: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier,
    imageVector: ImageVector,
    visualTransformation: VisualTransformation
) {


    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = text,
                color = ORANGE
            )
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        singleLine = true,
        maxLines = 1,
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ORANGE,
            focusedLabelColor = ORANGE,
            cursorColor = ORANGE,
            focusedTextColor = ORANGE,
            unfocusedBorderColor = ORANGE,
            unfocusedTextColor = ORANGE,
        ),
        trailingIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = ORANGE
            )
        }

    )

}


