package com.example.academia.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ButtonCustom(
    onClick: () -> Unit,
    modifier: Modifier,
    color: Color,
    text: String
) {

    androidx.compose.material.Button(
        onClick = onClick,
        modifier = modifier,
        colors = androidx.compose.material.ButtonDefaults.buttonColors(color)
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Black
        )
    }


}
