package com.example.academia.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.academia.R
import com.example.academia.itemlist.CardExercices
import com.example.academia.itemlist.TrainingCard
import com.example.academia.ui.theme.BLACK
import com.example.academia.ui.theme.ORANGE
import com.example.academia.ui.theme.WHITE
import com.example.academia.viewmodel.AuthViewModel
import com.example.academia.viewmodel.TrainingViewModel
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
    trainingViewModel: TrainingViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val nameUser = viewModel.perfilUser().collectAsState(initial = "").value

    val listTraining = trainingViewModel.getTraining().collectAsState(mutableListOf()).value

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = ORANGE,
                title = {
                    Text(
                        text = nameUser,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = BLACK
                    )
                },
                actions = {

                    IconButton(
                        onClick = {
                            val alertDialog = AlertDialog.Builder(context)
                            alertDialog.setTitle("Logout")
                            alertDialog.setMessage("Do you want to exit the application?")
                            alertDialog.setPositiveButton("Yes") {_,_->
                                FirebaseAuth.getInstance().signOut()
                                navController.navigate("loginScreen")
                            }
                            alertDialog.setNegativeButton("No") {_,_->}.show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Logout",
                            tint = BLACK
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("saveTrainingScreen")
                },
                backgroundColor = ORANGE
            ) {
                if(listTraining.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Training"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Update"
                    )
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = ORANGE,
            ) {
                TextButton(
                    onClick = {
                        navController.navigate("home")
                    },
                    modifier = Modifier
                        .padding(start = 40.dp, end = 30.dp)
                ) {
                    Text(
                        text = "Training",
                        color = WHITE,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                TextButton(
                    onClick = {
                        navController.navigate("exercisesScreen")
                    },
                    modifier = Modifier
                        .padding(start = 50.dp, end = 30.dp)
                ) {
                    Text(
                        text = "Exercises",
                        color = BLACK,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.download),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )


            Column(
                modifier = Modifier
                    .padding(start = 20.dp, top = 100.dp, end = 20.dp)
            ) {
                LazyColumn {
                    itemsIndexed(listTraining) { _, training ->
                        TrainingCard(navController ,training, context, hiltViewModel())
                    }
                }
            }


        }

    }

}