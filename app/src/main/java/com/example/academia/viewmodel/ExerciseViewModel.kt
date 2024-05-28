package com.example.academia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import com.example.academia.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(private val exerciseRepository: ExerciseRepository): ViewModel(){

    fun setExercises(name: String, imageUri: String, observation: String, listenerAuth: ListenerAuth) {
        viewModelScope.launch {
            exerciseRepository.setExercises(name, imageUri, observation, listenerAuth)
        }
    }

    fun getExercises(): Flow<MutableList<Exercise>> {
        return exerciseRepository.getExercises()
    }

    fun updateExercises(id: String, name: String, imageUri: String, observation: String, listenerAuth: ListenerAuth) {
        exerciseRepository.updateExercises(id, name, imageUri, observation, listenerAuth)
    }

    fun deleteExercises(name: String, listenerAuth: ListenerAuth) {
        return exerciseRepository.deleteExercises(name, listenerAuth)
    }

}