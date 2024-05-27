package com.example.academia.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.academia.R
import com.example.academia.components.ButtonCustom
import com.example.academia.components.OutlinedTextFieldCustom
import com.example.academia.listener.ListenerAuth
import com.example.academia.ui.theme.ORANGE
import com.example.academia.viewmodel.AuthViewModel
import com.example.academia.viewmodel.ExerciseViewModel
import java.io.Serializable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    navController: NavController,
    viewlModel: AuthViewModel = hiltViewModel(),
    ) {

    val context = LocalContext.current

    var nameRegister by remember { mutableStateOf("") }
    var emailRegister by remember { mutableStateOf("") }
    var passwordRegister by remember { mutableStateOf("") }


    Scaffold{

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
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    androidx.compose.material.Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        colors = androidx.compose.material.ButtonDefaults.outlinedButtonColors(ORANGE),
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Image(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Come Back"
                        )
                    }
                }

                Text(
                    text = "Register",
                    color = ORANGE,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 10.dp)
                )

                OutlinedTextFieldCustom(
                    value = nameRegister,
                    onValueChange = {
                        nameRegister = it
                    },
                    text = "Name",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp),
                    visualTransformation = VisualTransformation.None,
                    imageVector = Icons.Default.Person
                )

                OutlinedTextFieldCustom(
                    value = emailRegister,
                    onValueChange = {
                        emailRegister = it
                    },
                    text = "Email",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 0.dp),
                    visualTransformation = VisualTransformation.None,
                    imageVector = Icons.Default.Email
                )

                OutlinedTextFieldCustom(
                    value = passwordRegister,
                    onValueChange = {
                        passwordRegister = it
                    },
                    text = "Password",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 0.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    imageVector = Icons.Default.Lock

                )

                ButtonCustom(
                    onClick = {
                        viewlModel.register(nameRegister, emailRegister, passwordRegister, object : ListenerAuth {
                            override fun onSucess(mensseger: String, screen: String) {
                                Toast.makeText(context, mensseger, Toast.LENGTH_LONG).show()
                                navController.popBackStack()
                            }

                            override fun onFailure(error: String) {
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }

                        })
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 0.dp),
                    color = ORANGE,
                    text = "Register"
                )

            }


        }



    }

}