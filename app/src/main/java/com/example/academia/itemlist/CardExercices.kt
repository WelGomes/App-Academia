package com.example.academia.itemlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.academia.model.Exercise
import com.example.academia.ui.theme.BLACK
import com.example.academia.ui.theme.ORANGE
import com.example.academia.ui.theme.WHITE

@Composable
fun CardExercices(
    exercise: Exercise,
    isSelected: Boolean,
    onSelect: (Exercise) -> Unit
) {

    Card(
        backgroundColor = if(isSelected) ORANGE else WHITE,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(100.dp)
            .clickable { onSelect(exercise) }
    ) {

        ConstraintLayout(
            modifier = Modifier
                .padding(20.dp)
        ) {

            val (img, txtName, txtDescription) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = exercise.imageUri)
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
                text = exercise.name.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if(isSelected) WHITE else ORANGE,
                modifier = Modifier
                    .constrainAs(txtName) {
                        start.linkTo(img.end, margin = 30.dp)
                        top.linkTo(parent.top, margin = 10.dp)
                        end.linkTo(parent.end, margin = 30.dp)
                    }
            )

            Text(
                text = exercise.observation.toString(),
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