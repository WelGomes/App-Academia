package com.example.academia.model

data class Training(
    val name: String? = null,
    val description: String? = null,
    val date: String? = null,
    val exercise: List<Exercise>? = null,
    val id: String? = null
)