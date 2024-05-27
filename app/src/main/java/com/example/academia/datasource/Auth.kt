package com.example.academia.datasource

import com.example.academia.listener.ListenerAuth
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class Auth @Inject constructor(){

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _isUserLogin = MutableStateFlow(false)
    private val isUserLogin: StateFlow<Boolean> = _isUserLogin

    private val _nome = MutableStateFlow("")
    private val nome: StateFlow<String> = _nome


    fun register(name: String, email: String, password: String, listenerAuth: ListenerAuth) {

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            listenerAuth.onFailure("fill in the name and email and password fields!")
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { register ->
                if(register.isSuccessful) {
                    val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()

                    val userMap = hashMapOf(
                        "nome" to name,
                        "email" to email,
                        "userID" to userID
                    )

                    db.collection("users").document(userID).set(userMap).addOnCompleteListener {
                        listenerAuth.onSucess("Sucess in registering user", "loginScreen")
                    }.addOnFailureListener {
                        listenerAuth.onFailure("Error unexpected")
                    }
                }
            }.addOnFailureListener {exception ->
                val error = when(exception) {
                    is FirebaseAuthUserCollisionException -> "This account has already been registered"
                    is FirebaseAuthWeakPasswordException -> "The password must be at least 6 characters long"
                    is FirebaseNetworkException -> "No internet connection"
                    else -> "Invalid email"
                }
                listenerAuth.onFailure(error)
            }

        }

    }

    fun login(email: String, password: String, listenerAuth: ListenerAuth) {

        if(email.isEmpty() || password.isEmpty()) {
            listenerAuth.onFailure("fill in the email and password fields!")
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {autentication ->
                if(autentication.isSuccessful) {
                    listenerAuth.onSucess("sucessfull login", "home")
                }
            }.addOnFailureListener {exeption ->
                val error = when(exeption) {
                    is FirebaseAuthInvalidCredentialsException -> "The email or password entered is wrong"
                    is FirebaseNetworkException -> "No internet connection"
                    else -> "Invalid email"
                }
                listenerAuth.onFailure(error)
            }

        }

    }


    fun isUserLogin(): Flow<Boolean> {

        val userLogin = FirebaseAuth.getInstance().currentUser

        _isUserLogin.value = userLogin != null

        return isUserLogin

    }


    fun perfilUser(): Flow<String> {

        val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        db.collection("users").document(userID).get().addOnCompleteListener {documentSnapshot ->
            if(documentSnapshot.isSuccessful) {
                val nome = documentSnapshot.result.getString("nome").toString()
                _nome.value = nome
            }
        }
        return nome

    }

}