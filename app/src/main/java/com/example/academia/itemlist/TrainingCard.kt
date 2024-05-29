package com.example.academia.itemlist

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.util.fastForEachIndexed
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.academia.model.Training
import com.example.academia.ui.theme.BLACK
import com.example.academia.ui.theme.ORANGE

@Composable
fun TrainingCard(
    training: Training
) {

    val firstExercise = training.exercise?.firstOrNull()

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(200.dp)
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                val (txtNameTraining, txtDescription, txtDate) = createRefs()

                Text(
                    text = "Date - ${training.date.toString()}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BLACK,
                    modifier = Modifier
                        .constrainAs(txtDate) {

                        }
                )

                Text(
                    text = training.name.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ORANGE,
                    modifier = Modifier
                        .constrainAs(txtNameTraining) {

                        }
                )

                Text(
                    text = training.description.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BLACK,
                    modifier = Modifier
                        .constrainAs(txtDescription) {

                        }
                )



            }
        }

        training.exercise?.forEach() { index->
            Card {
                ConstraintLayout {

                    val (img, txtNameExercises, txtObservation) = createRefs()

                    Text(
                        text = index.name.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ORANGE,
                        modifier = Modifier
                            .constrainAs(txtNameExercises) {

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

                            }
                    )
                }
            }
        }

    }
}