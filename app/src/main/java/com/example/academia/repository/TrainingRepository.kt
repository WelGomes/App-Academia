package com.example.academia.repository

import com.example.academia.datasource.DataSourceTraining
import com.example.academia.listener.ListenerAuth
import com.example.academia.model.Exercise
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TrainingRepository @Inject constructor(private val dataSourceTraining: DataSourceTraining){

    fun setTraining(name: String, description: String, date: String, exercise: List<Exercise>, listenerAuth: ListenerAuth) {
        dataSourceTraining.setTraining(name, description, date, exercise, listenerAuth)
    }

}