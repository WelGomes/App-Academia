package com.example.academia.itemlist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.academia.model.Training
import com.example.academia.ui.theme.BLACK
import com.example.academia.ui.theme.ORANGE

@Composable
fun TrainingCard(
    training: Training,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        
        ConstraintLayout(
            modifier = Modifier
                .padding(20.dp)
                .height(500.dp)
        ) {
            val (img, txtNameTraining, txtDescription, txtDate, txtNameExercises, txtObservation) = createRefs()

            val firstExercise = training.exercise?.firstOrNull()

            Text(
                text = training.name.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ORANGE,
                modifier = Modifier
                    .constrainAs(txtNameTraining) {
                        start.linkTo(parent.start, margin = 50.dp)
                        end.linkTo(parent.end, margin = 50.dp)
                    }
            )

            Text(
                text = " - ${training.date.toString()}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = BLACK,
                modifier = Modifier
                    .constrainAs(txtDate) {
                        start.linkTo(txtNameTraining.start, margin = 20.dp)
                    }
            )

            Text(
                text = training.description.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = BLACK,
                modifier = Modifier
                    .constrainAs(txtDescription) {
                        top.linkTo(txtNameTraining.top, margin = 20.dp)
                        start.linkTo(parent.start, margin = 50.dp)
                        end.linkTo(parent.end, margin = 50.dp)
                    }
            )

            Text(
                text = firstExercise?.name.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ORANGE,
                modifier = Modifier
                    .constrainAs(txtNameExercises) {
                        top.linkTo(txtDescription.top, margin = 20.dp)
                        start.linkTo(parent.start, margin = 50.dp)
                        end.linkTo(parent.end, margin = 50.dp)
                    }
            )

            if(firstExercise != null) {
                androidx.compose.foundation.Image(
                    painter = rememberImagePainter(
                        data = firstExercise.imageUri,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(60.dp)
                        .constrainAs(img) {
                            top.linkTo(txtNameExercises.top, margin = 10.dp)
                        }
                )

            }

            Text(
                text = firstExercise?.observation.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = BLACK,
                modifier = Modifier
                    .constrainAs(txtObservation) {
                        top.linkTo(img.top, margin = 20.dp)
                        start.linkTo(parent.start, margin = 50.dp)
                        end.linkTo(parent.end, margin = 50.dp)
                    }
            )
        }

    }

}