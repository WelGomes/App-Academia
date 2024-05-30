package com.example.academia.datasource

import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import com.example.academia.model.Training
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

import javax.inject.Inject

class DataSourceTraining @Inject constructor() {

    val db = FirebaseFirestore.getInstance()


    private val _fullTraining = MutableStateFlow<MutableList<Training>>(mutableListOf())

    fun setTraining(
        name: String,
        description: String,
        date: String,
        exercise: List<Exercise>,
        listenerAuth: ListenerAuth
    ) {

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            listenerAuth.onFailure("Usuário não autenticado")
            return
        }

        val trainingMap = hashMapOf(
            "name" to name,
            "description" to description,
            "date" to date,
            "exercise" to exercise,
        )

        if (name.isEmpty() || exercise.isEmpty()) {
            listenerAuth.onFailure("Put at least the name and exercise")
        } else {
            db.collection("training").document(userId).collection("training_user").document().run {
                trainingMap["id"] = id
                set(trainingMap).addOnCompleteListener {
                    listenerAuth.onSucess("Training saved successfully", "home")
                }.addOnFailureListener {
                    listenerAuth.onFailure("Error saving training")
                }
            }
        }

    }

    fun getTraining(): Flow<MutableList<Training>> {

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return _fullTraining


        db.collection("training").document(userId).collection("training_user").get()
            .addOnSuccessListener {
                val listTraining: MutableList<Training> = mutableListOf()

                for (document in it.documents) {
                    val training = document.toObject(Training::class.java)
                    if (training != null) {
                        listTraining.add(training)
                    }
                }
                _fullTraining.value = listTraining
            }
        return _fullTraining

    }

    fun deleteTraining(trainingId: String, listenerAuth: ListenerAuth) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            db.collection("training").document(userId).collection("training_user").document(trainingId).delete()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        listenerAuth.onSucess("Treino excluído com sucesso", "home")
                    } else {
                        listenerAuth.onFailure("Erro ao excluir o treino")
                    }
                }
                .addOnFailureListener { exception ->
                    val error = when (exception) {
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        else -> exception.message ?: "Erro desconhecido"
                    }
                    listenerAuth.onFailure(error)
                }
            }
        }
    }


}