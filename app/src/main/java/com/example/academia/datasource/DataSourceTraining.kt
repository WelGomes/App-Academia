package com.example.academia.datasource

import androidx.compose.runtime.State
import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import com.example.academia.model.Training
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DataSourceTraining @Inject constructor() {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance().currentUser

    private val _fullTraining = MutableStateFlow<MutableList<Training>>(mutableListOf())
    private val fullTraining: StateFlow<MutableList<Training>> = _fullTraining

    fun setTraining(name: String, description: String, date: String, exercise: List<Exercise>, listenerAuth: ListenerAuth) {

        val trainingMap = hashMapOf(
            "name" to name,
            "description" to description,
            "date" to date,
            "exercise" to exercise
        )

        if(name.isEmpty()) {
            listenerAuth.onFailure("Put at least the name")
        }else {
            if(auth != null) {
                val uid = auth.uid

                db.collection("training").document(uid).set(trainingMap).addOnCompleteListener {
                    listenerAuth.onSucess("Training saved successfully", "home")
                }.addOnFailureListener {
                    listenerAuth.onFailure("Error saving training")
                }

            }
        }

    }


    fun getTraining(): Flow<MutableList<Training>> {

        val listTraining: MutableList<Training> = mutableListOf()

        db.collection("training").get().addOnCompleteListener { querySnapshot ->
            if(querySnapshot.isSuccessful) {
                for(documnet in querySnapshot.result) {
                    val training = documnet.toObject(Training::class.java)
                    listTraining.add(training)
                    _fullTraining.value = listTraining
                }
            }
        }

        return fullTraining
    }

}