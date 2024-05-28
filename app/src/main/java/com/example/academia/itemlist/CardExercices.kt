package com.example.academia.itemlist

import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.FabPosition
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
import coil.compose.rememberImagePainter
import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import com.example.academia.ui.theme.BLACK
import com.example.academia.ui.theme.ORANGE
import com.example.academia.ui.theme.WHITE
import com.example.academia.viewmodel.ExerciseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CardExercices(
    listExercise: MutableList<Exercise>,
    position: Int
) {

    val nameExercise = listExercise[position].name
    val imageUriExercise = listExercise[position].imageUri
    val observationExercise = listExercise[position].observation

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

            val (img, txtName, txtDescription) = createRefs()

            Image(
                painter = rememberImagePainter(
                    data = imageUriExercise,
                    builder = {
                        crossfade(true)
                    }
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
                text = nameExercise.toString(),
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