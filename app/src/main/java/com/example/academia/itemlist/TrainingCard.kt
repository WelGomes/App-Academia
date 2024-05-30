package com.example.academia.itemlist

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Training
import com.example.academia.ui.theme.BLACK
import com.example.academia.ui.theme.ORANGE
import com.example.academia.viewmodel.TrainingViewModel

@Composable
fun TrainingCard(
    navController: NavController,
    training: Training,
    context: Context,
    listTraining: TrainingViewModel
) {

    val firstExercise = training.exercise?.firstOrNull()

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(200.dp),
            backgroundColor = Color.Gray
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                val (txtNameTraining, btnUpdate, btnDelete, txtDescription, txtDate) = createRefs()

                Text(
                    text = "Date - ${training.date.toString()}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BLACK,
                    modifier = Modifier
                        .constrainAs(txtDate) {
                            start.linkTo(parent.start, margin = 0.dp)
                            top.linkTo(parent.top, margin = 10.dp)
                        }
                )

                IconButton(
                    onClick = {
                        navController.navigate("updateTrainingScreen")
                    },
                    modifier = Modifier
                        .constrainAs(btnUpdate) {
                            start.linkTo(txtDate.start, margin = 200.dp)
                        },
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Update",
                        tint = Color.Black
                    )
                }

                IconButton(
                    onClick = {
                        val alertDialog = AlertDialog.Builder(context)
                        alertDialog.setTitle("Delete exercises")
                        alertDialog.setMessage("Do you want to delete the application?")
                        alertDialog.setPositiveButton("Yes") {_,_->
                            listTraining.deleteTraining(training.id.orEmpty() ,object : ListenerAuth {
                                override fun onSucess(mensseger: String, screen: String) {
                                    Toast.makeText(context, mensseger, Toast.LENGTH_SHORT).show()
                                    navController.navigate(screen)
                                }

                                override fun onFailure(error: String) {
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                }

                            })
                        }
                        alertDialog.setNegativeButton("No") {_,_->}.show()
                    },
                    modifier = Modifier
                        .constrainAs(btnDelete) {
                            start.linkTo(btnUpdate.end, margin = -6.dp)
                        },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete training",
                        tint = Color.Red
                    )
                }



                Text(
                    text = training.name.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ORANGE,
                    modifier = Modifier
                        .constrainAs(txtNameTraining) {
                            start.linkTo(parent.start, margin = 50.dp)
                            top.linkTo(btnDelete.bottom, margin = 5.dp)
                            end.linkTo(parent.end, margin = 50.dp)
                        }
                )

                Text(
                    text = training.description.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BLACK,
                    modifier = Modifier
                        .constrainAs(txtDescription) {
                            start.linkTo(parent.start, margin = 50.dp)
                            top.linkTo(txtNameTraining.bottom, margin = 5.dp)
                            end.linkTo(parent.end, margin = 50.dp)
                        }
                )

            }
        }

        training.exercise?.forEach() { index->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(150.dp)
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .padding(10.dp)
                ) {

                    val (img, txtNameExercises, txtObservation) = createRefs()

                    Text(
                        text = index.name.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ORANGE,
                        modifier = Modifier
                            .constrainAs(txtNameExercises) {
                                start.linkTo(parent.start, margin = 50.dp)
                                top.linkTo(parent.top, margin = 0.dp)
                                end. linkTo(parent.end, margin = 50.dp)
                            }
                    )

                    if (firstExercise != null) {
                        androidx.compose.foundation.Image(
                            painter = rememberImagePainter(
                                data = index.imageUri,
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(60.dp)
                                .constrainAs(img) {
                                    top.linkTo(txtNameExercises.bottom, margin = 2.dp)
                                }
                        )

                    }

                    Text(
                        text = index.observation.toString(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BLACK,
                        modifier = Modifier
                            .constrainAs(txtObservation) {
                                start.linkTo(parent.start, margin = 50.dp)
                                top.linkTo(img.bottom, margin = 0.dp)
                                end.linkTo(parent.end, margin = 50.dp)
                            }
                    )
                }
            }
        }

    }
}