package com.example.academia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.academia.repository.ExerciseRepository
import com.example.academia.ui.theme.AcademiaTheme
import com.example.academia.view.Exercises
import com.example.academia.view.Home
import com.example.academia.view.LoginScreen
import com.example.academia.view.RegisterScreen
import com.example.academia.view.SaveExercise
import com.example.academia.view.SaveTrainingScreen
import com.example.academia.view.UpdateExercisesScreen
import com.example.academia.view.UpdateTrainingScreen
import com.example.academia.viewmodel.AuthViewModel
import com.example.academia.viewmodel.ExerciseViewModel
import com.example.academia.viewmodel.TrainingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcademiaTheme {

                val navController = rememberNavController()
                val viewModel: AuthViewModel = hiltViewModel()
                val exerciseViewModel: ExerciseViewModel = hiltViewModel()
                val trainingViewModel: TrainingViewModel = hiltViewModel()

                NavHost(
                    navController = navController,
                    startDestination = "loginScreen"
                ) {

                    composable("loginScreen") {
                        LoginScreen(navController, viewModel)
                    }

                    composable("registerScreen") {
                        RegisterScreen(navController, viewModel)
                    }

                    composable("home") {
                        Home(navController, viewModel, trainingViewModel)
                    }

                    composable("exercisesScreen") {
                        Exercises(navController, viewModel, exerciseViewModel)
                    }

                    composable("saveExerciseScreen") {
                        SaveExercise(navController, exerciseViewModel)
                    }

                    composable("saveTrainingScreen") {
                        SaveTrainingScreen(navController, exerciseViewModel, trainingViewModel)
                    }

                    composable(
                        "updateExercisesScreen/{id}/{name}/{imageUri}/{observation}",
                        arguments = listOf(
                            navArgument("id") { defaultValue = "" },
                            navArgument("name") { defaultValue = "" },
                            navArgument("imageUri") { defaultValue = "" },
                            navArgument("observation") { defaultValue = "" },
                        )
                    ) {
                        UpdateExercisesScreen(
                            navController,
                            exerciseViewModel,
                            id = it.arguments?.getString("id").toString(),
                            name = it.arguments?.getString("name").toString(),
                            imageUri = it.arguments?.getString("imageUri").toString(),
                            observation = it.arguments?.getString("observation").toString(),
                        )
                    }

                    composable(
                        "updateTrainingScreen/{idTraining}/{nameTraining}/{descriptionTraining}/{dateTraining}",
                        arguments = listOf(
                            navArgument("idTraining") { defaultValue = "" },
                            navArgument("nameTraining") { defaultValue = "" },
                            navArgument("descriptionTraining") { defaultValue = "" },
                            navArgument("dateTraining") { defaultValue = "" },
                            navArgument("exercisesTraining") { defaultValue = "" },
                        )
                    ) {
                        UpdateTrainingScreen(
                            navController,
                            trainingViewModel,
                            exerciseViewModel,
                            id = it.arguments?.getString("idTraining").toString(),
                            name = it.arguments?.getString("nameTraining").toString(),
                            description = it.arguments?.getString("descriptionTraining").toString(),
                            date = it.arguments?.getString("dateTraining").toString()
                            )
                    }

                }

            }
        }
    }
}