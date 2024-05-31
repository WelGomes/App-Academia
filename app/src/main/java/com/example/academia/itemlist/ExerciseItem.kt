package com.example.academia.itemlist

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier.padding(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .align(Alignment.Top),
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageUriExercise)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = null
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nameExercise.orEmpty(),
                    style = MaterialTheme.typography.h6
                )

                Text(
                    text = observationExercise.orEmpty(),
                    style = MaterialTheme.typography.body2
                )
            }

            val encodedUrl = URLEncoder.encode(imageUriExercise, StandardCharsets.UTF_8.toString())

            IconButton(
                onClick = {
                    navController.navigate("updateExercisesScreen/${id}/${nameExercise}/${encodedUrl}/${observationExercise}")
                }
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Training")
            }
            IconButton(
                onClick = {
                    val alertDialog = AlertDialog.Builder(context)
                    alertDialog.setTitle("Delete exercises")
                    alertDialog.setMessage("Do you want to delete the application?")
                    alertDialog.setPositiveButton("Yes") { _, _ ->
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
                    alertDialog.setNegativeButton("No") { _, _ -> }.show()
                }
            ) {
                Icon(
                    Icons.Default.Delete, contentDescription = "Delete Training",
                    tint = Color.Red
                )
            }
        }
    }
}