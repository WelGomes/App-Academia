package com.example.academia.datasource

import androidx.core.net.toUri
import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DataSourceExercises @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()

    private val _fullExercise = MutableStateFlow<MutableList<Exercise>>(mutableListOf())
    private val fullExercise: StateFlow<MutableList<Exercise>> = _fullExercise

    private val storage: FirebaseStorage = Firebase.storage("gs://academia-3f972.appspot.com")

    private fun uploadImage(
        image: String,
        document: DocumentReference,
        exerciseMap: HashMap<String, String>,
        listenerAuth: ListenerAuth
    ) {
        val ref = storage.reference.child("exercises/${document.id}")

        ref.putFile(image.toUri()).continueWith {
            if (it.isSuccessful) {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    exerciseMap["imageUri"] = uri.toString()
                    saveExercise(exerciseMap, document, listenerAuth)
                }.addOnFailureListener {
                    saveExercise(exerciseMap, document, listenerAuth)
                }
            } else {
                saveExercise(exerciseMap, document, listenerAuth)
            }
        }
    }

    fun setExercises(
        name: String,
        imageUri: String,
        observation: String,
        listenerAuth: ListenerAuth
    ) {

        val exerciseMap = hashMapOf(
            "name" to name,
            "imageUri" to imageUri,
            "observation" to observation
        )

        if (name.isEmpty() || imageUri.isEmpty()) {
            listenerAuth.onFailure("fill in the name and email and Image fields!")
        } else {
            db.collection("exercises").document().run {
                uploadImage(imageUri, this, exerciseMap, listenerAuth)
            }

        }

    }

    private fun saveExercise(
        exerciseMap: HashMap<String, String>,
        document: DocumentReference,
        listenerAuth: ListenerAuth
    ) {
        exerciseMap["id"] = document.id
        document.set(exerciseMap).addOnCompleteListener {
            listenerAuth.onSucess("Exercise saved", "exercisesScreen")
        }.addOnFailureListener { exception ->
            val error = when (exception) {
                is FirebaseNetworkException -> "No internet connection"
                else -> exception.message ?: "Unknow error"
            }
            listenerAuth.onFailure(error)
        }
    }

    fun getExercises(): Flow<MutableList<Exercise>> {

        val listExercise: MutableList<Exercise> = mutableListOf()

        db.collection("exercises").get().addOnCompleteListener { querySnapshoot ->
            if (querySnapshoot.isSuccessful) {
                for (document in querySnapshoot.result) {
                    val exercise = document.toObject(Exercise::class.java)
                    listExercise.add(exercise)
                    _fullExercise.value = listExercise
                }
            }
        }
        return fullExercise
    }


    fun updateExercises(
        id: String,
        name: String,
        imageUri: String,
        observation: String,
        listenerAuth: ListenerAuth
    ) {
        if (name.isEmpty() && imageUri.isEmpty() && observation.isEmpty()) {
            listenerAuth.onSucess("", "exercisesScreen")
        } else {
            val updates = hashMapOf<String, Any>().apply {
                when {
                    (!name.isEmpty()) -> put("name", name)
                    (!imageUri.isEmpty()) -> put("imageUri", imageUri)
                    (!observation.isEmpty()) -> put("observation", observation)
                }
            }

            db.collection("exercises").document(id).update(updates).addOnCompleteListener {
                listenerAuth.onSucess("Successfully modified exercise", "exercisesScreen")
            }.addOnFailureListener {
                listenerAuth.onFailure("Failed to change exercise")
            }
        }
    }


    fun deleteExercises(id: String, listenerAuth: ListenerAuth) {
        db.collection("exercises").document(id).delete().addOnCompleteListener {
            listenerAuth.onSucess("Exercise successfully deleted", "ExercisesScreen")
        }.addOnFailureListener { exception ->
            val error = when (exception) {
                is FirebaseNetworkException -> "No internet connection"
                else -> exception.message ?: "Unknown error"
            }
            listenerAuth.onFailure(error)
        }
    }


}