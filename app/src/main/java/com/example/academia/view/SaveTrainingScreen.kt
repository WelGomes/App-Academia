package com.example.academia.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.academia.components.ButtonCustom
import com.example.academia.itemlist.CardExercices
import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import com.example.academia.ui.theme.ORANGE
import com.example.academia.viewmodel.ExerciseViewModel
import com.example.academia.viewmodel.TrainingViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SaveTrainingScreen(
    navController: NavController,
    exerciseViewModel: ExerciseViewModel,
    trainingViewModel: TrainingViewModel
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var nameTraining by remember { mutableStateOf("") }
    var descriptionTraining by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    val selectedExercises = remember { mutableStateListOf<Exercise>() }

    val focusManager = LocalFocusManager.current
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()



    if(showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false},
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState
                            .selectedDateMillis?.let { millis ->
                                selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(millis))
                            }
                        showDatePickerDialog = false
                    }
                ) {
                    Text(text = "Select the date")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }


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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()

        ) {

            OutlinedTextField(
                value = nameTraining,
                onValueChange = {
                    nameTraining = it
                },
                label = {
                    Text(
                        text = "Name",
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

            OutlinedTextField(
                value = descriptionTraining,
                onValueChange = {
                    descriptionTraining = it
                },
                label = {
                    Text(
                        text = "Description",
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

            TextField(
                value = selectedDate,
                onValueChange = { },
                Modifier
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .onFocusEvent {
                        if (it.isFocused) {
                            showDatePickerDialog = true
                            focusManager.clearFocus(force = true)
                        }
                    },
                label = {
                    Text(
                        text = "Date",
                        color = ORANGE
                    )
                },
                readOnly = true
            )

            ButtonCustom(
                onClick = {
                    scope.launch {
                        trainingViewModel.setTraining(nameTraining, descriptionTraining, selectedDate, selectedExercises.toList(), object : ListenerAuth {
                            override fun onSucess(mensseger: String, screen: String) {
                                Toast.makeText(context, mensseger, Toast.LENGTH_SHORT).show()
                                navController.navigate(screen)
                            }

                            override fun onFailure(error: String) {
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            }

                        })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                color = ORANGE,
                text = "Save"
            )

            val listExercise = exerciseViewModel.getExercises().collectAsState(mutableListOf()).value

            LazyColumn {
                itemsIndexed(listExercise) { _, exercise ->
                    val isSelected = selectedExercises.any { it.id == exercise.id }
                    CardExercices(
                        exercise ,
                        isSelected,
                        onSelect = { selectedExercise ->
                            if(selectedExercises.contains(selectedExercise)) {
                                selectedExercises.remove(selectedExercise)
                            } else {
                                selectedExercises.add(selectedExercise)
                            }
                        }
                        )
                }
            }

        }


    }

}


