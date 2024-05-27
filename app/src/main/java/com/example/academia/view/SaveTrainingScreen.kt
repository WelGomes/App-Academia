package com.example.academia.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.academia.ui.theme.ORANGE

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SaveTrainingScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = ORANGE,
                title = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Come back"
                        )
                    }

                    Text(
                        text = "Add Traning",
                        modifier = Modifier
                            .padding(start = 70.dp)
                    )
                }
            )
        }
    ){

    }

}


@Preview
@Composable
fun SaveTrainingScreenPreview() {
    SaveTrainingScreen(rememberNavController())
}