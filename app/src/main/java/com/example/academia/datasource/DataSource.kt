package com.example.academia.datasource

import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DataSource @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()

    private val _fullExercise = MutableStateFlow<MutableList<Exercise>>(mutableListOf())
    private val fullExercise: StateFlow<MutableList<Exercise>> = _fullExercise

    fun setExercises(name: String, imageUri: String, observation: String, listenerAuth: ListenerAuth) {

        val exerciseMap = hashMapOf(
            "name" to name,
            "imageUri" to imageUri,
            "observation" to observation
        )

        if(name.isEmpty() || imageUri.isEmpty()) {
            listenerAuth.onFailure("fill in the name and email and Image fields!")
        } else {
            db.collection("exercises").add(exerciseMap).addOnCompleteListener {
                listenerAuth.onSucess("Exercise saved", "exercisesScreen")
            }.addOnFailureListener { exception ->
                val error = when(exception) {
                    is FirebaseNetworkException -> "No internet connection"
                    else -> exception.message?: "Unknow error"
                }
                listenerAuth.onFailure(error)
            }
        }

    }

    fun getExercises(): Flow<MutableList<Exercise>> {

        val listExercise: MutableList<Exercise> = mutableListOf()

        db.collection("exercises").get().addOnCompleteListener {querySnapshoot ->
            if(querySnapshoot.isSuccessful) {
                for (document in querySnapshoot.result) {
                    val exercise = document.toObject(Exercise::class.java)
                    listExercise.add(exercise)
                    _fullExercise.value = listExercise
                }
            }
        }
        return fullExercise

    }

    fun deleteExercises(name: String, listenerAuth: ListenerAuth) {

        val reference = db.collection("exercises").document(name)

        reference.get().addOnCompleteListener { document ->
            if(document.isSuccessful) {
                reference.delete().addOnCompleteListener {
                    listenerAuth.onSucess("Exercise successfully deleted", "ExercisesScreen")
                }.addOnFailureListener { exception ->
                    val error = when(exception) {
                        is FirebaseNetworkException -> "No internet connection"
                        else -> exception.message ?: "Unknown error"
                    }
                    listenerAuth.onFailure(error)
                }
            } else {
                listenerAuth.onFailure("The exercise does not exist")
            }
        }.addOnFailureListener { exception ->
            val error = when(exception) {
                is FirebaseNetworkException -> "No internet connection"
                else -> exception.message ?: "Unknown error"
            }
            listenerAuth.onFailure(error)
        }

    }

}