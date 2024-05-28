package com.example.academia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import com.example.academia.repository.TrainingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(private val trainingViewModel: TrainingRepository): ViewModel() {


    fun setTraining(name: String, description: String, date: String, exercise: List<Exercise>, listenerAuth: ListenerAuth) {
        viewModelScope.launch {
            trainingViewModel.setTraining(name, description, date, exercise, listenerAuth)
        }
    }

}