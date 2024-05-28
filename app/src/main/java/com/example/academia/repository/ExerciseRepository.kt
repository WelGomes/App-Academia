package com.example.academia.repository

import com.example.academia.datasource.DataSource
import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ExerciseRepository @Inject constructor(private val dataSource: DataSource) {

    fun setExercises(name: String, imageUri: String, observation: String, listenerAuth: ListenerAuth) {
        dataSource.setExercises(name, imageUri, observation, listenerAuth)
    }

    fun getExercises(): Flow<MutableList<Exercise>> {
        return dataSource.getExercises()
    }

    fun updateExercises(id: String, name: String, imageUri: String, observation: String, listenerAuth: ListenerAuth) {
        dataSource.updateExercises(id, name, imageUri, observation, listenerAuth)
    }

    fun deleteExercises(id: String, listenerAuth: ListenerAuth) {
        return dataSource.deleteExercises(id, listenerAuth)
    }

}