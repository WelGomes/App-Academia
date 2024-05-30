package com.example.academia.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.academia.components.ButtonCustom
import com.example.academia.listener.ListenerAuth
import com.example.academia.ui.theme.ORANGE
import kotlinx.coroutines.launch

@Composable
fun UpdateTrainingScreen(
    navController: NavController
) {

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
                        text = "Add exercise",
                        modifier = Modifier
                            .padding(start = 70.dp)
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = nameExercise,
                onValueChange = {
                    nameExercise = it
                },
                label = {
                    Text(
                        text = nameExercise,
                        color = ORANGE
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 100.dp, end = 20.dp),
                singleLine = true,
                maxLines = 1,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ORANGE,
                    focusedLabelColor = ORANGE,
                    cursorColor = ORANGE,
                    focusedTextColor = ORANGE,
                    unfocusedBorderColor = ORANGE,
                    unfocusedTextColor = ORANGE,
                ),
            )

            Row(
                Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp)
                ) {
                    Text(
                        text = "Pick image",
                        color = ORANGE
                    )
                }

                img?.let {
                    Image(
                        painter = rememberAsyncImagePainter(model = img),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .padding(top = 30.dp)
                    )
                }

            }

            OutlinedTextField(
                value = observationExercise,
                onValueChange = {
                    observationExercise = it
                },
                label = {
                    Text(
                        text = "Observation",
                        color = ORANGE
                    )
                },
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                    .height(150.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ORANGE,
                    focusedLabelColor = ORANGE,
                    cursorColor = ORANGE,
                    focusedTextColor = ORANGE,
                    unfocusedBorderColor = ORANGE,
                    unfocusedTextColor = ORANGE,
                )
            )

            ButtonCustom(
                onClick = {
                    scope.launch {
                        exerciseViewModel.updateExercises(id, nameExercise, img, observationExercise, object : ListenerAuth {
                            override fun onSucess(mensseger: String, screen: String) {
                                Toast.makeText(context, mensseger, Toast.LENGTH_LONG).show()
                                navController.navigate(screen)
                            }

                            override fun onFailure(error: String) {
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }

                        })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                color = ORANGE,
                text = "Update"
            )

        }
    }


}