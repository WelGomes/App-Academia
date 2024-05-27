package com.example.academia.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.academia.R
import com.example.academia.components.ButtonCustom
import com.example.academia.components.OutlinedTextFieldCustom
import com.example.academia.listener.ListenerAuth
import com.example.academia.ui.theme.ORANGE
import com.example.academia.viewmodel.AuthViewModel
import java.io.Serializable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
    ) {

    val context = LocalContext.current
    var userLogin = viewModel.isUserLogin().collectAsState(initial = false).value
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(userLogin) {
        if(userLogin) {
            navController.navigate("home")
        } else {
            userLogin = false
        }
    }

       Scaffold {

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

                   Icon(
                       imageVector = Icons.Default.AccountCircle,
                       contentDescription = null,
                       modifier = Modifier
                           .height(100.dp)
                           .width(100.dp),
                       tint = ORANGE
                   )

                   OutlinedTextFieldCustom(
                       value = email,
                       onValueChange = {
                           email = it
                       },
                       text = "Email",
                       keyboardOptions = KeyboardOptions(
                           keyboardType = KeyboardType.Email
                       ),
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp),
                       visualTransformation = VisualTransformation.None,
                       imageVector = Icons.Default.Email
                   )

                   OutlinedTextFieldCustom(
                       value = password,
                       onValueChange = {
                           password = it
                       },
                       text = "Password",
                       keyboardOptions = KeyboardOptions(
                           keyboardType = KeyboardType.Password
                       ),
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp),
                       visualTransformation = PasswordVisualTransformation(),
                       imageVector = Icons.Default.Lock
                   )


                   ButtonCustom(
                       onClick = {
                           viewModel.login(email, password, object : ListenerAuth {
                               override fun onSucess(mensseger: String, screen: String) {
                                   Toast.makeText(context, mensseger, Toast.LENGTH_LONG).show()
                                   navController.navigate(screen)
                               }

                               override fun onFailure(error: String) {
                                   Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                               }

                           })
                       },
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 20.dp, top = 10.dp, end = 20.dp),
                       color = ORANGE,
                       text = "Login"
                   )

                   TextButton(
                       onClick = {
                            navController.navigate("registerScreen")
                       },

                       ) {
                       Text(
                           text = "Don't have an account? Register now!",
                           color = ORANGE
                       )
                   }
               }

           }




           }
       }




@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}