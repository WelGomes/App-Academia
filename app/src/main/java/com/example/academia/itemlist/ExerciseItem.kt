package com.example.academia.itemlist

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import com.example.academia.ui.theme.BLACK
import com.example.academia.ui.theme.ORANGE
import com.example.academia.ui.theme.WHITE
import com.example.academia.viewmodel.ExerciseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ExerciseItem(
    navController: NavController = rememberNavController(),
    position: Int,
    listExercise: MutableList<Exercise>,
    context: Context,
    exerciseViewModel: ExerciseViewModel
) {

    val nameExercise = listExercise[position].name
    val imageUriExercise = listExercise[position].imageUri
    val observationExercise = listExercise[position].observation
    val id = listExercise[position].id

    val scope = rememberCoroutineScope()

    Card(
        backgroundColor = WHITE,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        ConstraintLayout(
            modifier = Modifier
                .padding(20.dp)
        ) {

            val (img, txtName, txtDescription, btnDelete, btnUpdate) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageUriExercise)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
                    .constrainAs(img) {
                        start.linkTo(parent.start, margin = 10.dp)
                        top.linkTo(parent.top, margin = 0.dp)
                    }
            )

            Text(
                text = nameExercise.orEmpty(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ORANGE,
                modifier = Modifier
                    .constrainAs(txtName) {
                        start.linkTo(img.end, margin = 30.dp)
                        top.linkTo(parent.top, margin = 10.dp)
                        end.linkTo(parent.end, margin = 30.dp)
                    }
            )

            IconButton(
                onClick = {
                    val alertDialog = AlertDialog.Builder(context)
                    alertDialog.setTitle("Delete exercises")
                    alertDialog.setMessage("Do you want to delete the application?")
                    alertDialog.setPositiveButton("Yes") {_,_->
                        exerciseViewModel.deleteExercises(id.toString(), object : ListenerAuth {
                            override fun onSucess(mensseger: String, screen: String) {
                                Toast.makeText(context, mensseger, Toast.LENGTH_LONG).show()
                                navController.navigate(screen)
                            }

                            override fun onFailure(error: String) {
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }

                        })

                        scope.launch(Dispatchers.Main) {
                            listExercise.removeAt(position)
                            navController.navigate("exercisesScreen")
                        }
                    }
                    alertDialog.setNegativeButton("No") {_,_->}.show()
                },
                modifier = Modifier
                    .constrainAs(btnDelete) {
                        start.linkTo(txtName.end, margin = 20.dp)
                        top.linkTo(parent.top, margin = 0.dp)
                        bottom.linkTo(parent.bottom, margin = 55.dp)
                    },
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete exercise",
                    tint = Color.Red
                )
            }

            val encodedUrl = URLEncoder.encode(imageUriExercise, StandardCharsets.UTF_8.toString())

            IconButton(
                onClick = {
                    navController.navigate("updateExercisesScreen/${id}/${nameExercise}/${encodedUrl}/${observationExercise}")
                },
                modifier = Modifier
                    .constrainAs(btnUpdate) {
                        start.linkTo(btnDelete.end, margin = 15.dp)
                        top.linkTo(parent.top, margin = 0.dp)
                        bottom.linkTo(parent.bottom, margin = 55.dp)
                    },
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Update",
                    tint = Color.Black
                )
            }


            Text(
                text = observationExercise.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = BLACK,
                modifier = Modifier
                    .constrainAs(txtDescription) {
                        start.linkTo(img.end, margin = 30.dp)
                        top.linkTo(img.bottom, margin = 10.dp)
                        end.linkTo(parent.end, margin = 30.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    }
            )

        }

    }

}