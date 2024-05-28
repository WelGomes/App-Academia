package com.example.academia.datasource

import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class DataSourceTraining @Inject constructor() {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance().currentUser
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


}